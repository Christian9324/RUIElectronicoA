package com.example.electrorui.ui.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.RecyclerView
import com.example.electrorui.databinding.RecyclerItemMsjBinding
import com.example.electrorui.networkApi.model.PinDatosModel
import com.example.electrorui.usecase.model.Mensaje

class MensajesAdapter(

    var mensajesPinDatos: List<Mensaje>

) : RecyclerView.Adapter<MensajesAdapter.ViewHolder>() {

//    lateinit var lastItem : Mensaje
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerItemMsjBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mensajeP = mensajesPinDatos[position]
        holder.bind(mensajeP)
    }

    override fun getItemCount() = mensajesPinDatos.size

    class ViewHolder(private val binding: RecyclerItemMsjBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind( mensaje : Mensaje){
            bindDetailMsg(binding.mensajeDatos, mensaje.mensaje)
            binding.mensajeDatos.setTextIsSelectable(true)
        }

        private fun bindDetailMsg(mensajeET: TextView, mensaje: String) {
//            mensajeET.text = Html.fromHtml(mensaje)
            mensajeET.text = HtmlCompat.fromHtml(mensaje, HtmlCompat.FROM_HTML_MODE_LEGACY)
        }
    }

}
