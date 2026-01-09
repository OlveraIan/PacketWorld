package uv.tc.packetworldmovil

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.BindingAdapter
import uv.tc.packetworldmovil.databinding.ActivityEditarPerfilBinding
import uv.tc.packetworldmovil.pojo.Colaborador
import uv.tc.packetworldmovil.pojo.NombreConductor
import com.google.gson.Gson
import com.koushikdutta.ion.Ion
import java.io.ByteArrayOutputStream

// Importación corregida para Base64
import android.util.Base64.decode
import uv.tc.packetworldmovil.dto.Respuesta
import uv.tc.packetworldmovil.util.Constantes

object ImageBindingAdapters {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImageUrl(view: android.widget.ImageView, url: String?) {
        if (url != null && url.isNotEmpty()) {
            try {
                // Usar el Base64.decode importado y corregido
                val imageBytes = decode(url, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                view.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Log.e("ImageBindingAdapter", "Error decoding image: ${e.message}")
            }
        } else {
            view.setImageResource(R.drawable.outline_account_circle_24) // Imagen por defecto
        }
    }
}

class EditarPerfil : AppCompatActivity() {
    private var fotoPerfilByte: ByteArray? = null
    private lateinit var binding: ActivityEditarPerfilBinding
    private var colaborador: Colaborador? = null
    private val roles = listOf("Conductor", "Administrador", "Ejecutivo de tienda")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_editar_perfil)
        obtenerColaborador()
        binding.colaborador = this.colaborador

        if (colaborador != null) {
            llenarFormulario(colaborador!!)
            descargarfoto(colaborador!!.idColaborador!!)
        }

        binding.etNumeroPersonal.isEnabled = false
        binding.layoutNumeroPersonal.isEnabled = false
        binding.spRol.isEnabled = false

        // Corregido el layout del adapter para el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        binding.spRol.adapter = adapter

        binding.btnSubirFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            seleccionarFoto.launch(intent)
        }

        binding.btnEditar.setOnClickListener {
            if (validarCampos()) {
                colaborador?.let {
                    it.nombre = binding.etNombre.text.toString()
                    it.apellidoPaterno = binding.etApellidoP.text.toString()
                    it.apellidoMaterno = binding.etApellidoM.text.toString()
                    it.curp = binding.etCurp.text.toString()
                    it.correo = binding.etEmail.text.toString()
                    it.contrasena = binding.etContrasena.text.toString()
                    it.numeroLicencia = binding.etNumeroLicencia.text.toString()
                    actualizarInformacion(it)
                }
            }
        }
    }

    private fun validarCampos(): Boolean {
        var camposValidos = true
        if (binding.etNombre.text.toString().isEmpty()) {
            binding.etNombre.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etApellidoP.text.toString().isEmpty()) {
            binding.etApellidoP.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etApellidoM.text.toString().isEmpty()) {
            binding.etApellidoM.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etCurp.text.toString().isEmpty()) {
            binding.etCurp.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etContrasena.text.toString().isEmpty()) {
            binding.etContrasena.error = "Campo requerido"
            camposValidos = false
        }
        if (binding.etNumeroLicencia.text.toString().isEmpty()) {
            binding.etNumeroLicencia.error = "Campo requerido"
            camposValidos = false
        }
        return camposValidos
    }

    private fun llenarFormulario(colaborador: Colaborador) {
        val selectedRoleIndex = roles.indexOf(colaborador.rol)
        if (selectedRoleIndex >= 0) {
            binding.spRol.setSelection(selectedRoleIndex)
        }
    }

    private fun actualizarInformacion(colaborador: Colaborador) {
        val gson = Gson()
        val json = gson.toJson(colaborador)
        Ion.with(this@EditarPerfil)
            .load("PUT", "${Constantes().URL_WS}colaborador/editar") // Corregido Constantes
            .setHeader("Content-Type", "application/json")
            .setStringBody(json)
            .asString()
            .setCallback { e, result ->
                if (e == null && result != null) {
                    verificarResultadoEdicion(result)
                } else {
                    Toast.makeText(this@EditarPerfil, "Error en la petición para actualizar la información.", Toast.LENGTH_LONG).show()
                    Log.e("Error", e?.message.toString())
                }
            }
    }

    private fun verificarResultadoEdicion(result: String) {
        val gson = Gson()
        val msj: Respuesta = gson.fromJson(result, Respuesta::class.java)
        Toast.makeText(this@EditarPerfil, msj.mensaje, Toast.LENGTH_LONG).show()
        if (!msj.error) {
            NombreConductor.usuario = "${colaborador!!.nombre} ${colaborador!!.apellidoPaterno} ${colaborador!!.apellidoMaterno}"
            val gson = Gson()
            val stringColaborador = gson.toJson(colaborador)
            val intent = Intent(this@EditarPerfil, DetalleColaboradorActivity::class.java)
            intent.putExtra("Colaborador", stringColaborador)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this@EditarPerfil, "Error en la petición para actualizar la información.", Toast.LENGTH_LONG).show()
        }
    }

    private fun obtenerColaborador() {
        val gson = Gson()
        val stringColaborador = intent.getStringExtra("Colaborador")
        colaborador = gson.fromJson(stringColaborador, Colaborador::class.java)
        binding.colaborador = this.colaborador
    }

    private fun descargarfoto(idColaborador: Int) {
        binding.progressBar.visibility = View.VISIBLE
        binding.imgPerfil.visibility = View.GONE
        Ion.with(this)
            .load("${Constantes().URL_WS}colaborador/obtenerFoto/$idColaborador") // Corregido Constantes
            .asString()
            .setCallback {
                    e, result ->
                binding.progressBar.visibility = View.GONE
                if (e == null) {
                    visualizarFotoPerfil(result)
                    binding.imgPerfil.visibility = View.VISIBLE
                } else {
                    Log.e("Error", "Error al descargar la foto de perfil: ${e.message}")
                }
            }
    }

    private fun visualizarFotoPerfil(json: String) {
        val fetchedColaborador = Gson().fromJson(json, Colaborador::class.java)
        fetchedColaborador?.fotografiaBase64?.let {
            if (it.isNotEmpty()) {
                try {
                    // Se utiliza el decode importado y corregido
                    val imageBytes = decode(it, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                    binding.imgPerfil.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e("EditarPerfil", "Error al decodificar la foto de perfil: ${e.message}")
                }
            }
        }
    }

    private val seleccionarFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.data?.let { uri ->
                fotoPerfilByte = uriToByteArray(uri)
                fotoPerfilByte?.let { byteArray ->
                    colaborador?.idColaborador?.let { id ->
                        subirFotoDePerfil(id, byteArray)
                    }
                }
            }
        }
    }

    private fun uriToByteArray(uri: Uri): ByteArray? {
        return try {
            contentResolver.openInputStream(uri)?.use {
                val bitmap = BitmapFactory.decodeStream(it)
                ByteArrayOutputStream().apply {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, this)
                }.toByteArray()
            }
        } catch (e: Exception) {
            Log.e("EditarPerfil", "Error al convertir URI a ByteArray: ${e.message}")
            null
        }
    }

    private fun subirFotoDePerfil(idColaborador: Int, fotoPerfil: ByteArray) {
        Ion.with(this)
            .load("PUT", "${Constantes().URL_WS}colaborador/subirFoto/$idColaborador")
            .setByteArrayBody(fotoPerfil).asString()
            .setCallback {
                    e, result ->
                if (e == null) {
                    val mensaje = Gson().fromJson(result, Respuesta::class.java)
                    if (!mensaje.error) {
                        Log.d("Mensaje", mensaje.mensaje)
                        descargarfoto(idColaborador)
                    }
                } else {
                    Log.e("Error", "Error al subir la foto de perfil: ${e.message}")
                }
            }
    }
}
