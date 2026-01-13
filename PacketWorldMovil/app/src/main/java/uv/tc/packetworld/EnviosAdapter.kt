package uv.tc.packetworld

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uv.tc.packetworld.pojo.Envio
import uv.tc.packetworld.databinding.ItemEnvioBinding


class EnviosAdapter (private val envios: List<Envio>,
                     private val onClick: (Envio) -> Unit
) : RecyclerView.Adapter<EnviosAdapter.EnvioViewHolder>() {
    inner class EnvioViewHolder(val binding: ItemEnvioBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnvioViewHolder {
        val binding = ItemEnvioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EnvioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EnvioViewHolder, position: Int) {
        val envio = envios[position]

        holder.binding.tvGuia.text = envio.guia
        holder.binding.tvCPCliente.text = envio.cpCliente
        holder.binding.tvEstado.text = envio.estadoEnvio

        holder.binding.root.setOnClickListener {
            onClick(envio)
        }
    }

    override fun getItemCount() = envios.size
}
