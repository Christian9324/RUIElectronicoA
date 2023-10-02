package com.example.electrorui.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.databinding.ViewRegistroItemBinding
import com.example.electrorui.networkApi.model.RegistroNacionalidadModel
import com.example.electrorui.usecase.model.RegistroNacionalidad

class NacionalidadesAdapter(

    var registroNacionalidad : List<RegistroNacionalidad>,
    private val nacionalidadesClickedListener: (RegistroNacionalidad, Int) -> Unit // se modifico esta linea labda

) : RecyclerView.Adapter<NacionalidadesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewRegistroItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registroNacionalidad[position]
        holder.bind(registro)
        holder.itemView.setOnClickListener {
            nacionalidadesClickedListener(registro, position) // Se modifico esta linea lambda
        }
    }

    override fun getItemCount() = registroNacionalidad.size

    class ViewHolder(private val binding: ViewRegistroItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(registro : RegistroNacionalidad){

            val A = registro.AS_hombres + registro.AS_mujeresNoEmb + registro.AS_mujeresEmb
            val AA = registro.AA_NNAs_hombres + registro.AA_NNAs_mujeresNoEmb + registro.AA_NNAs_mujeresEmb
            val NA = registro.NNAsA_hombres + registro.NNAsA_mujeresNoEmb + registro.NNAsA_mujeresEmb
            val NS = registro.NNAsS_hombres + registro.NNAsS_mujeresNoEmb + registro.NNAsS_mujeresEmb

            binding.tvPais.text = registro.iso3
//            binding.tvTotales.text = (A + registro.nucleosFamiliares + AA + NA + NS).toString()
            binding.tvTotales.text = (A + AA + NA + NS).toString()
        }
    }
}


//--------------------
//------------------ Anterior modelo sin lambdas

//interface NacionalidadesClickedListener{
//    fun onNacionalidadesClicked(nacionalidad: RegistroNacionalidad, position: Int)
//}
//
//class NacionalidadesAdapter(
//
//    var registroNacionalidad : List<RegistroNacionalidad>,
//    private val nacionalidadesClickedListener: NacionalidadesClickedListener
//
//) : RecyclerView.Adapter<NacionalidadesAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
////        val view = LayoutInflater
////                .from(parent.context)
////                .inflate(R.layout.view_registro_item, parent, false)
//        val binding = ViewRegistroItemBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent,
//            false
//        )
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val registro = registroNacionalidad[position]
//        holder.bind(registro)
//        holder.itemView.setOnClickListener {
//            nacionalidadesClickedListener.onNacionalidadesClicked(registro, position)
//        }
//    }
//
//    override fun getItemCount() = registroNacionalidad.size
//
//    class ViewHolder(private val binding: ViewRegistroItemBinding) : RecyclerView.ViewHolder(binding.root){
//        fun bind(registro : RegistroNacionalidad){
//
//            val A = registro.AS_hombres + registro.AS_mujeresNoEmb + registro.AS_mujeresEmb
//            val AA = registro.AA_NNAs_hombres + registro.AA_NNAs_mujeresNoEmb + registro.AA_NNAs_mujeresEmb
//            val NA = registro.NNAsA_hombres + registro.NNAsA_mujeresNoEmb + registro.NNAsA_mujeresEmb
//            val NS = registro.NNAsS_hombres + registro.NNAsS_mujeresNoEmb + registro.NNAsS_mujeresEmb
//
//            binding.tvPais.text = registro.nacionalidad
////            binding.tvTotales.text = (A + registro.nucleosFamiliares + AA + NA + NS).toString()
//            binding.tvTotales.text = (A + AA + NA + NS).toString()
//        }
//    }
//}