package com.example.electrorui.ui.adapters

import android.graphics.Color
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.R
import com.example.electrorui.databinding.ViewANnaItemBinding
import com.example.electrorui.usecase.model.RegistroNombres

class RescateNombresAdapter(

    var registroNombres : List<RegistroNombres>,
//    private val nacionalidadesClickedListener: (RegistroNombres, Int) -> Unit, // se modifico esta linea labda
    private val nacionalidadesLongClickListener: (RegistroNombres, Int) -> Unit, // se modifico esta linea labda

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
        holder.itemView.setOnLongClickListener {
            nacionalidadesLongClickListener(registro, registro.idNombres)
            return@setOnLongClickListener true
        }
//        holder.itemView.setOnClickListener {
//            nacionalidadesClickedListener(registro, registro.idNombres) // Se modifico esta linea lambda
//        }
//        holder.itemView.setOnVeryLongClickListener {
//            nacionalidadesLongClickListener(registro, registro.idNombres)
//        }
    }

    fun View.setOnVeryLongClickListener(listener: () -> Unit) {
        setOnTouchListener(object : View.OnTouchListener {

            private val longClickDuration = 2500L
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

    override fun getItemCount() = registroNombres.size

    class ViewHolder(private val binding: ViewANnaItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(registro : RegistroNombres){

            val edad = registro.getEdad()
            val tipo : String
            if (edad > 18){
                tipo = "A"
                val typedValue = TypedValue()
                binding.root.context.theme.resolveAttribute(R.attr.colorBtnN, typedValue, true)
                val colorA = typedValue.data
                binding.LLi.setBackgroundColor(colorA)
//                binding.LLi.setBackgroundColor(Color.parseColor("#FFBC955C"))
//                binding.LLi.setBackgroundColor(R.attr.colorBtnN)
            } else {
                tipo = "NNA"
                val typedValue = TypedValue()
                binding.root.context.theme.resolveAttribute(R.attr.colorBtnF, typedValue, true)
                val colorN = typedValue.data
                binding.LLi.setBackgroundColor(colorN)
//                binding.LLi.setBackgroundColor(Color.parseColor("#FF047832"))
//                binding.LLi.setBackgroundColor(R.attr.colorBtnF)
            }


            val nombreCompleto = "${registro.apellidos} ${registro.nombre}"
            binding.tvNombre.text = nombreCompleto
            binding.tvTipo.text = tipo

        }
    }
}
