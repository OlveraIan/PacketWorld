package uv.tc.packetworld

import android.R
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uv.tc.packetworld.modelo.EstadoEnvio
import uv.tc.packetworld.databinding.FragmentDetalleEnvioBinding
import uv.tc.packetworld.modelo.DetalleEnvioViewModel
import uv.tc.packetworld.pojo.Envio
import uv.tc.packetworld.red.PacketWorldSingleton

class DetalleEnvioFragment : Fragment() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    private val apiService = PacketWorldSingleton.apiService

    private var _binding: FragmentDetalleEnvioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetalleEnvioViewModel by viewModels()

    private var envioActual: Envio? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetalleEnvioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val envioId = arguments?.getString("envioId") ?: return
        Log.d("envioId:",envioId)
        configurarSpinnerEstados()
        configurarBotones()

        viewModel.cargarDetalle(envioId)
        observarViewModel()
    }

    private fun configurarSpinnerEstados() {
        val estados = EstadoEnvio.values().map { it.valor }
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            estados
        )
        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )
        binding.spEstado.adapter = adapter
    }

    private fun configurarBotones() {

        binding.btnVerPaquetes.setOnClickListener {
            envioActual?.let { envio ->
                val bundle = Bundle().apply {
                    putString("numeroGuia", envio.guia)
                }
                findNavController().navigate(
                    uv.tc.packetworld.R.id.action_detalleEnvioFragment_to_paquetesFragment,
                    bundle
                )
            }
        }

        //Actualizar estado
        /*binding.btnActualizarEstado.setOnClickListener {
            val estado = binding.spEstado.selectedItem
            val json = "{ \"estadoEnvio\": \"$estado\" }"
            viewModelScope.launch {
                apiService.cambiarEstado(json)
                Toast.makeText(requireContext(), "Estado actualizado", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    private fun observarViewModel() {

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (it) View.VISIBLE else View.GONE
        }

        viewModel.envio.observe(viewLifecycleOwner) { envio ->
            envioActual = envio

            binding.tvFolio.text = envio.guia
            binding.tvCliente.text = envio.nombreCliente + " " + envio.apPaternoCliente
            binding.tvOrigen.text = envio.sucursal + " - " + envio.sucursalCalle+""+envio.sucursalCP
            binding.tvDestino.text = envio.calleCliente+""+envio.numeroCliente+""+envio.coloniaCliente+""+envio.cpCliente
            binding.tvEstadoActual.text = "Estado actual: ${envio.estadoEnvio}"

            // Seleccionar estado actual en el spinner
            val index = EstadoEnvio.values()
                .indexOfFirst { it.valor == envio.estadoEnvio }
            if (index >= 0) {
                binding.spEstado.setSelection(index)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
