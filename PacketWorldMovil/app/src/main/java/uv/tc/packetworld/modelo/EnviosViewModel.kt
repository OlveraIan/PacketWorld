package uv.tc.packetworld.modelo

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.packetworld.pojo.Envio
import uv.tc.packetworld.red.PacketWorldAPIService
import uv.tc.packetworld.red.PacketWorldSingleton
import uv.tc.packetworld.util.AdministradorDeSesion

class EnviosViewModel  : ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val apiService = PacketWorldSingleton.apiService
    private val _envios = MutableLiveData<List<Envio>>()
    val envios: LiveData<List<Envio>> = _envios

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarEnvios(colaboradorId: String) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = apiService.getEnviosPorConductor(numeroPersonal = colaboradorId)

                if (response?.isSuccessful!!) {
                    val body = response.body()
                    if (body != null) {
                        _envios.value = body
                    } else {
                        _error.value = "Sin envios asignados"
                    }
                } else {
                    _error.value = "Error ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión"
            } finally {
                _loading.value = false
            }
        }
    }
}