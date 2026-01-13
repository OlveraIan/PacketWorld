package uv.tc.packetworld

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import uv.tc.packetworld.databinding.ActivityLoginBinding
import uv.tc.packetworld.modelo.LoginViewModel
import uv.tc.packetworld.pojo.Colaborador
import uv.tc.packetworld.util.AdministradorDeSesion

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeViewModel()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val numeroPersonal = binding.etNumeroPersonal.text.toString()
            val password = binding.etPassword.text.toString()

            if (numeroPersonal.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(numeroPersonal, password)
        }
    }

    private fun observeViewModel() {
        viewModel.loginResult.observe(this) { result ->
            if (result.error.not()) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Bienvenido ${result.colaborador?.nombre}", Toast.LENGTH_SHORT).show()
                AdministradorDeSesion.guardarColaborador(this, result.colaborador!!)
                val intent = Intent(this@LoginActivity, EnviosActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Log.d("Error de Red", result.message)
                Toast.makeText(this, "Error en la conexion", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility =
                if (isLoading) View.VISIBLE else View.GONE
        }
    }
}