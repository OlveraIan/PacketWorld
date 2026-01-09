package uv.tc.packetworldmovil

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import uv.tc.packetworldmovil.databinding.ActivityDetallesColaboradorBinding
import uv.tc.packetworldmovil.pojo.Colaborador
import uv.tc.packetworldmovil.util.Constantes


class DetalleColaboradorActivity : AppCompatActivity() {
// ... (tus imports se mantienen igual)

    class DetalleColaboradorActivity : AppCompatActivity() {

        private lateinit var binding: ActivityDetallesColaboradorBinding
        private var colaborador: Colaborador? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // 1. Inflar Binding
            binding = ActivityDetallesColaboradorBinding.inflate(layoutInflater)
            setContentView(binding.root)

            title = "Detalle del colaborador"

            // 2. Obtener datos
            obtenerColaborador()

            // 3. Verificar si el colaborador existe antes de llenar la UI
            colaborador?.let { col ->
                llenarDetalles(col)
                col.idColaborador?.let { id -> descargarfoto(id) }
            } ?: run {
                Toast.makeText(this, "Error: No se recibieron datos del colaborador", Toast.LENGTH_LONG).show()
            }

            configurarBotones()
        }

        private fun configurarBotones() {
            binding.btnEditar.setOnClickListener {
                colaborador?.let {
                    val intent = Intent(this, EditarPerfil::class.java)
                    intent.putExtra("Colaborador", Gson().toJson(it))
                    startActivity(intent)
                }
            }

        }

        private fun llenarDetalles(it: Colaborador) {
            // Uso de safe call con binding
            binding.apply {
                etNombreCompleto.text = "${it.nombre} ${it.apellidoPaterno} ${it.apellidoMaterno}".uppercase()
                etNombre.text = "Nombre : ${it.nombre}"
                etApellidoP.text = "Apellido Paterno: ${it.apellidoPaterno}"
                etApellidoM.text = "Apellido Materno: ${it.apellidoMaterno}"
                etCurp.text = "CURP: ${it.curp}"
                etEmail.text = "Correo: ${it.correo}"
                etNumeroPersonal.text = "Numero Personal: ${it.numeroPersonal}"
                etRol.text = "Rol: ${it.rol}"
            }
        }

        private fun obtenerColaborador() {
            val stringColaborador = intent.getStringExtra("Colaborador")
            if (!stringColaborador.isNullOrEmpty()) {
                colaborador = Gson().fromJson(stringColaborador, Colaborador::class.java)
            }
        }

        private fun descargarfoto(idColaborador: Int) {
            binding.progressBar.visibility = View.VISIBLE
            binding.perfil.visibility = View.GONE

            Ion.with(this)
                .load("GET", "${Constantes().URL_WS}colaborador/obtenerFoto/$idColaborador")
                .asString()
                .setCallback { e, result ->
                    binding.progressBar.visibility = View.GONE
                    if (e == null && result != null) {
                        visualizarFotoPerfil(result)
                        binding.perfil.visibility = View.VISIBLE
                    } else {
                        Log.e("Error", "Error al descargar la foto: ${e?.message}")
                    }
                }
        }

        private fun visualizarFotoPerfil(json: String) {
            try {
                val colFoto = Gson().fromJson(json, Colaborador::class.java)
                val base64String = colFoto?.fotografiaBase64

                if (!base64String.isNullOrEmpty()) {
                    val imageBytes = Base64.decode(base64String, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding.perfil.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                Log.e("Error", "Error al procesar la imagen: ${e.message}")
            }
        }
    }
}
