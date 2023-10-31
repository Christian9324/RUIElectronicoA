package com.example.electrorui.ui.adapters

import android.graphics.Color
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.databinding.ViewFamiliaItemBinding
import com.example.electrorui.databinding.ViewFamiliasItemBinding
import com.example.electrorui.usecase.model.RegistroFamilias
import java.text.SimpleDateFormat
import java.util.Date

class RescateFamiliaAdapter(

    var registroFamilias : List<RegistroFamilias>,
//    private val registroFamiliasClickedListener: (RegistroFamilias, Int) -> Unit, // se modifico esta linea labda
    private val registroFamiliasLongClickListener: (RegistroFamilias , Int) -> Unit // se modifico esta linea labda

) : RecyclerView.Adapter<RescateFamiliaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewFamiliaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = registroFamilias[position]
        holder.bind(registro)
//        holder.itemView.setOnClickListener {
//            registroFamiliasClickedListener(registro, registro.idF) // Se modifico esta linea lambda
//        }
        holder.itemView.setOnLongClickListener {
            registroFamiliasLongClickListener(registro, registro.idF)
            return@setOnLongClickListener true
        }
//        holder.itemView.setOnVeryLongClickListener {
//            registroFamiliasLongClickListener(registro, registro.idF)
//        }
    }

    fun View.setOnVeryLongClickListener(listener: () -> Unit) {
        setOnTouchListener(object : View.OnTouchListener {

            private val longClickDuration = 1500L
            private val handler = Handler()

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.action == MotionEvent.ACTION_DOWN) {
                    handler.postDelayed({ listener.invoke() }, longClickDuration)
                } else if (event?.action == MotionEvent.ACTION_UP) {
                    handler.removeCallbacksAndMessages(null)
                }
                return true
            }
        })
    }

    override fun getItemCount() = registroFamilias.size

    class ViewHolder(private val binding: ViewFamiliaItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(registro : RegistroFamilias){

            val edad = registro.getEdad()
            val tipo : String
            if (edad > 18){
                tipo = "A"
                binding.LLfam.setBackgroundColor(Color.parseColor("#FF047832"))
            } else {
                tipo = "NNA"
                binding.LLfam.setBackgroundColor(Color.parseColor("#FF9F2241"))
            }

            binding.tvNombreC.text = "${registro.apellidos} ${registro.nombre}".toString()
            binding.tvIso.text = registro.iso3.toString()
            binding.tvTipo.text = tipo
        }
    }
}
