package com.example.electrorui.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.electrorui.databinding.ActivityRescateNombresBinding
import com.example.electrorui.ui.adapters.RescateNombresAdapter
import com.example.electrorui.ui.viewModel.RescatesNombres_AVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RescateNombresActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NACIONALIDAD = "RescateNombresActivity:nacionalidad"
    }

    private lateinit var binding : ActivityRescateNombresBinding
    private lateinit var nacNombresAdapter : RescateNombresAdapter
    private val dataActivityViewM : RescatesNombres_AVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRescateNombresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nacionalidadRecibida = intent.getStringExtra(EXTRA_NACIONALIDAD)
        dataActivityViewM.paisPadre.value = nacionalidadRecibida
        binding.spinnerPAIS.setText(nacionalidadRecibida)

        val nacio  = emptyList<String>()

        var autocompleteArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nacio)
        binding.spinnerPAIS.threshold = 1
        binding.spinnerPAIS.setAdapter(autocompleteArrayAdapter)

        binding.spinnerPAIS.setOnItemClickListener { adapterView, view, i, l ->
            hideKeyboard()
            dataActivityViewM.paisPadre.value = binding.spinnerPAIS.text.toString()
        }

        dataActivityViewM.paisPadre.observe(this){
            dataActivityViewM.onCreate()
        }

        nacNombresAdapter = RescateNombresAdapter(emptyList()){
            data , pos ->
            val intentRegistroNombres = Intent(this, NombresModActivity::class.java)
            intentRegistroNombres.putExtra(
                NombresModActivity.EXTRA_IDNOMBRE_DB,
                pos
            )
            startActivity(intentRegistroNombres)
        }
        binding.recyclerNombres.adapter = nacNombresAdapter

        dataActivityViewM.datosNombres.observe(this){
            nacNombresAdapter.registroNombres = it
            nacNombresAdapter.notifyDataSetChanged()
        }


        dataActivityViewM.paises.observe(this){
            binding.spinnerPAIS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it))
            autocompleteArrayAdapter.notifyDataSetChanged()
        }

        binding.btnPersona.setOnClickListener {
            val intentRegistroNombre = Intent(applicationContext, NombresActivity::class.java)
            intentRegistroNombre.putExtra(NombresActivity.EXTRA_NACIONALIDAD, binding.spinnerPAIS.text.toString())
            startActivity(intentRegistroNombre)
        }

        binding.btnGuardar.setOnClickListener {
            Toast.makeText(applicationContext, "Guardando información", Toast.LENGTH_SHORT).show()
            finish()
        }

        dataActivityViewM.onCreate()

    }
    override fun onResume() {
        super.onResume()
        dataActivityViewM.onCreate()
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}