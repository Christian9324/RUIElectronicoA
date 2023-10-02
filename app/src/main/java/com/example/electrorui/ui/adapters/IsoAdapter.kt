package com.example.electrorui.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.databinding.ViewRegistroItemBinding
import com.example.electrorui.usecase.model.Iso

class IsoAdapter(

    var registroByIso : List<Iso>,
    private val isoClickedListener: (Iso, Int) -> Unit // se modifico esta linea labda

) : RecyclerView.Adapter<IsoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewRegistroItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registroByIso[position]
        holder.bind(registro)
        holder.itemView.setOnClickListener {
            isoClickedListener(registro, position) // Se modifico esta linea lambda
        }
    }

    override fun getItemCount() = registroByIso.size

    class ViewHolder(private val binding: ViewRegistroItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(registro : Iso){

            binding.tvPais.text = registro.iso3
            binding.tvTotales.text = registro.conteo.toString()

        }
    }
}
