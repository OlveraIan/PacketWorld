package uv.tc.packetworld.modelo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.packetworld.pojo.LoginResult
import uv.tc.packetworld.pojo.Colaborador
import uv.tc.packetworld.red.PacketWorldSingleton

class LoginViewModel : ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val apiService = PacketWorldSingleton.apiService

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun login(numeroPersonal: String, password: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val gson: Gson = Gson()
                val response = apiService.login(numeroPersonal, password)
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.error.not()) {
                        if (loginResponse.colaborador?.idRol == 3) {
                            _loginResult.value =
                                LoginResult(error = false, colaborador = loginResponse.colaborador)
                        } else {
                            _loginResult.value = LoginResult(
                                error = true,
                                message = "Solo conductores pueden acceder a la aplicacion"
                            )
                        }
                    } else {
                        _loginResult.value = LoginResult(
                            error = true,
                            message = loginResponse?.mensaje ?: "Error desconocido"
                        )
                    }
                } else {
                    _loginResult.value = LoginResult(
                        error = true,
                        message = "Error en la respuesta del servidor: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _loginResult.value = LoginResult(
                    error = true,
                    message = "Error en la conexión: ${e.message}"
                )
            } finally {
                _loading.value = false
            }
        }

    }
}