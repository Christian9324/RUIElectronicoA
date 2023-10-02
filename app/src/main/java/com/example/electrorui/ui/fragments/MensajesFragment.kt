package com.example.electrorui.ui.fragments

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_LIST
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.electrorui.databinding.FragmentMensajesBinding
import com.example.electrorui.ui.adapters.MensajesAdapter
import com.example.electrorui.ui.viewModel.Mensajes_FVM
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MensajesFragment : Fragment() {

    private var _binding: FragmentMensajesBinding? = null
    private val dataActivityViewM : Mensajes_FVM by viewModels()
    private lateinit var mensajesAdapter : MensajesAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMensajesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.recyclerMensajes.adapter = MensajesAdapter(
            emptyList()
        )

        mensajesAdapter = MensajesAdapter( emptyList())
        binding.recyclerMensajes.adapter = mensajesAdapter
        dataActivityViewM.datosMensajes.observe(viewLifecycleOwner){
            mensajesAdapter.mensajesPinDatos = it
            mensajesAdapter.notifyDataSetChanged()
        }

        binding.shareWhatsapp.setOnClickListener {
            val msg = mensajesAdapter.mensajesPinDatos.get(mensajesAdapter.mensajesPinDatos.size -1)

            val cadena = HtmlCompat.fromHtml(msg.mensaje, FROM_HTML_SEPARATOR_LINE_BREAK_LIST).toString()

            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, cadena)
            try {
                requireActivity()!!.startActivity(whatsappIntent)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "Whatsapp no se encuentra instalado", Toast.LENGTH_SHORT).show()
            }
        }

        dataActivityViewM.onCreate()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        dataActivityViewM.onCreate()
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}