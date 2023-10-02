package com.example.electrorui.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.databinding.ViewANnaItemBinding
import com.example.electrorui.usecase.model.RegistroNombres

class RescateNombresAdapter(

    var registroNombres : List<RegistroNombres>,
    private val nacionalidadesClickedListener: (RegistroNombres, Int) -> Unit // se modifico esta linea labda

) : RecyclerView.Adapter<RescateNombresAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewANnaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registroNombres[position]
        holder.bind(registro)
        holder.itemView.setOnClickListener {
            nacionalidadesClickedListener(registro, registro.idNombres) // Se modifico esta linea lambda
        }
    }

    override fun getItemCount() = registroNombres.size

    class ViewHolder(private val binding: ViewANnaItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(registro : RegistroNombres){

            val edad = registro.getEdad()
            val tipo : String
            if (edad > 18){
                tipo = "A"
                binding.LLi.setBackgroundColor(Color.parseColor("#FFBC955C"))
            } else {
                tipo = "NNA"
                binding.LLi.setBackgroundColor(Color.parseColor("#FF047832"))
            }


            val nombreCompleto = "${registro.apellidos} ${registro.nombre}"
            binding.tvNombre.text = nombreCompleto
            binding.tvTipo.text = tipo

        }
    }
}
