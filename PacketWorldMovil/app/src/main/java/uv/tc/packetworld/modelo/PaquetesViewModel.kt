package uv.tc.packetworld.modelo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.packetworld.pojo.Paquete
import uv.tc.packetworld.red.PacketWorldSingleton

class PaquetesViewModel : ViewModel() {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val apiService = PacketWorldSingleton.apiService

    private val _paquetes = MutableLiveData<List<Paquete>>()
    val paquetes: LiveData<List<Paquete>> = _paquetes

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarPaquetes(numeroGuia: String) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = apiService.getPaquetesPorGuia(numeroGuia)

                if (response.isSuccessful) {
                    _paquetes.value = response.body() ?: emptyList()
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
