package uv.tc.packetworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uv.tc.packetworld.databinding.ItemPaqueteBinding
import uv.tc.packetworld.pojo.Paquete

class PaquetesAdapter(
    private val paquetes: List<Paquete>
) : RecyclerView.Adapter<PaquetesAdapter.PaqueteViewHolder>() {

    inner class PaqueteViewHolder(
        val binding: ItemPaqueteBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaqueteViewHolder {
        val binding = ItemPaqueteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PaqueteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PaqueteViewHolder, position: Int) {
        val paquete = paquetes[position]

        holder.binding.tvDescripcion.text = paquete.descripcion
        holder.binding.tvPeso.text = "Peso: ${paquete.peso} kg"
        holder.binding.tvDimensiones.text =
            "Dimensiones: ${paquete.alto} x ${paquete.ancho} x ${paquete.profundidad} cm"
    }

    override fun getItemCount() = paquetes.size
}
