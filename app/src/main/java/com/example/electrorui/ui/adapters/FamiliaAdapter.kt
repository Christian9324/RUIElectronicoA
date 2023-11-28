package com.example.electrorui.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.databinding.ViewFamiliasItemBinding
import com.example.electrorui.databinding.ViewRegistroItemBinding
import com.example.electrorui.usecase.model.Iso
import com.example.electrorui.usecase.model.NumerosFam
import com.example.electrorui.usecase.model.RegistroFamilias

class FamiliaAdapter(

    var registroByFamilia : List<NumerosFam>,
    private val familiasClickedListener: (NumerosFam, Int) -> Unit // se modifico esta linea labda

) : RecyclerView.Adapter<FamiliaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewFamiliasItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registroByFamilia[position]
        holder.bind(registro)
        holder.itemView.setOnClickListener {
            familiasClickedListener(registro, position) // Se modifico esta linea lambda
        }
    }

    override fun getItemCount() = registroByFamilia.size

    class ViewHolder(private val binding: ViewFamiliasItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(registro : NumerosFam){
            binding.tvTotal.text = registro.numFam.toString()
            binding.tvTotales.text = registro.totales.toString()
        }
    }
}
