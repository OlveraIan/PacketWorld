package uv.tc.packetworld

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import uv.tc.packetworld.databinding.FragmentPaquetesBinding
import uv.tc.packetworld.modelo.PaquetesViewModel

class PaquetesFragment : Fragment() {

    private var _binding: FragmentPaquetesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PaquetesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaquetesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numeroGuia = arguments?.getString("numeroGuia") ?: return
        binding.rvPaquetes.layoutManager = LinearLayoutManager(requireContext())

        viewModel.cargarPaquetes(numeroGuia)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (it) View.VISIBLE else View.GONE
        }

        viewModel.paquetes.observe(viewLifecycleOwner) { paquetes ->
            if (paquetes.isEmpty()) {
                binding.tvEmpty.visibility = View.VISIBLE
                binding.rvPaquetes.visibility = View.GONE
            } else {
                binding.tvEmpty.visibility = View.GONE
                binding.rvPaquetes.visibility = View.VISIBLE
                binding.rvPaquetes.adapter = PaquetesAdapter(paquetes)
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
