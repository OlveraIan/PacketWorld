package uv.tc.packetworld.modelo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.packetworld.pojo.Envio
import uv.tc.packetworld.red.PacketWorldSingleton.apiService

class DetalleEnvioViewModel : ViewModel() {


    private val _envio = MutableLiveData<Envio>()
    val envio: LiveData<Envio> = _envio

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun cargarDetalle(envioId: String) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val response = apiService.getDetalleEnvio(envioId)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _envio.value = body.firstOrNull()
                    } else {
                        _error.value = "Envio sin datos"
                    }
                } else {
                    _error.value = "Error ${response.code()}"
                }
            } catch (e: java.net.UnknownHostException) {
                // Error al resolver el nombre del host (DNS)
                _error.value = "Error: Host desconocido o URL incorrecta."
                Log.e("DetalleEnvioVM", "Error de host", e)
            } catch (e: java.net.SocketTimeoutException) {
                // El servidor no respondió a tiempo
                _error.value = "Error: Tiempo de espera agotado (Timeout)."
                Log.e("DetalleEnvioVM", "Error de timeout", e)
            } catch (e: Exception) {
                // Captura cualquier otra excepción (incluyendo NetworkOnMainThreadException si no usas coroutines correctamente, o problemas de permisos)
                _error.value = "Error de conexión"
                Log.e("DetalleEnvioVM", "Error de conexión general: ${e.message}", e) // ¡IMPORTANTE! Registra el mensaje y la traza
            } finally {
                _loading.value = false
            }
// ...
        }
    }
}
