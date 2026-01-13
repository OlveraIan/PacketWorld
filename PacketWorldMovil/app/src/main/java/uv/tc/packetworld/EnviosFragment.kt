package uv.tc.packetworld

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import uv.tc.packetworld.databinding.FragmentEnviosBinding
import uv.tc.packetworld.modelo.EnviosViewModel
import uv.tc.packetworld.util.AdministradorDeSesion

class EnviosFragment : Fragment() {

    private var _binding: FragmentEnviosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EnviosViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnviosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val colaboradorId = AdministradorDeSesion.obtenerRefColaborador(requireContext())
        Toast.makeText(requireContext(), "Colaborador ID: $colaboradorId", Toast.LENGTH_SHORT).show()
        binding.rvEnvios.layoutManager = LinearLayoutManager(requireContext())

        viewModel.cargarEnvios(colaboradorId!!)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (it) View.VISIBLE else View.GONE
        }

        viewModel.envios.observe(viewLifecycleOwner) { envios ->
            Log.d("EnviosFragment", "Datos recibidos. Cantidad: ${envios.size}")
            if (envios.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvEnvios.visibility = View.GONE
            } else {
                Log.d("EnviosFragment", "Asignando adaptador con ${envios.size} ítems.")

                binding.tvEmpty.visibility = View.GONE
                binding.rvEnvios.visibility = View.VISIBLE
                binding.rvEnvios.adapter =
                    EnviosAdapter(envios) { envio ->
                        val bundle = Bundle().apply {
                            putString("envioId", envio.guia)
                        }
                        findNavController().navigate(
                            R.id.action_enviosFragment_to_detalleEnvioFragment,
                            bundle
                        )
                    }

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
