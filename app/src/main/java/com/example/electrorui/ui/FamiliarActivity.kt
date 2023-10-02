package com.example.electrorui.ui

import android.R
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.electrorui.databinding.ActivityFamiliarBinding
import com.example.electrorui.ui.viewModel.Familiar_AVM
import com.example.electrorui.usecase.model.RegistroFamilias
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class FamiliarActivity : AppCompatActivity() {

    companion object{
        val EXTRA_FAMILIA = "FamiliarActivity:NoFamilia"
    }

    private lateinit var binding: ActivityFamiliarBinding
    private val dataActivityViewM : Familiar_AVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamiliarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        val nacio  = emptyList<String>()

        var autocompleteArrayAdapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, nacio)
        binding.spinnerPAISF.threshold = 1
        binding.spinnerPAISF.setAdapter(autocompleteArrayAdapter)
        dataActivityViewM.paises.observe(this){
            binding.spinnerPAISF.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it))
            autocompleteArrayAdapter.notifyDataSetChanged()
        }

        val adapterSpinner = ArrayAdapter.createFromResource(
            this, com.example.electrorui.R.array.tipo_parentesco, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerParentescoF.adapter = adapterSpinner


        binding.checkHombre.setOnClickListener {
            binding.checkHombre.isChecked = true
            binding.checkMujer.isChecked = false
        }

        binding.checkMujer.setOnClickListener {
            binding.checkHombre.isChecked = false
            binding.checkMujer.isChecked = true
        }

        binding.ldFechaNacimiento.setMaxLocalDate(org.threeten.bp.LocalDate.now())
        binding.ldFechaNacimiento.setMinLocalDate(
            org.threeten.bp.LocalDate.parse(
                "01/01/1920",
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            )
        )
//        binding.ldFechaNacimiento.localDate = org.threeten.bp.LocalDate.now()
        binding.ldFechaNacimiento.setOnLocalDatePickListener {date ->
            val fecha = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            hideKeyboard()

        }

        binding.btnGuardar.setOnClickListener {

            val nacionalidad = binding.spinnerPAISF.text.toString()
            val iso3D = dataActivityViewM.iso3.value
            val nom = binding.etNombreF.text.toString()
            val apellidos = binding.etApellidosF.text.toString()
            val noIdentidad = binding.etNoIdentidadF.text.toString()
            val parentesco = binding.spinnerParentescoF.selectedItem.toString()
            val fechaNacimiento = binding.ldFechaNacimiento.localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val sexo : Boolean = binding.checkHombre.isChecked
            val numeroFamilia = intent.getIntExtra(EXTRA_FAMILIA, 1)

            val paises = dataActivityViewM.paises.value
            val indexNacionalidad = paises?.indexOf(nacionalidad)

            val fechaNacimientoDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento)
            val fechaActual = Date(System.currentTimeMillis())
            val diferencia = fechaActual.time - fechaNacimientoDate?.time!!
            val edad : Int = (diferencia.toFloat() / (31536000000)).toInt()


            val datosRetorno = RegistroFamilias(
                1,
                nacionalidad,
                iso3D!![indexNacionalidad!!],
                nom,
                apellidos,
                noIdentidad,
                parentesco,
                fechaNacimiento,
                edad > 18,
                sexo,
                numeroFamilia,
            )

            dataActivityViewM.saveToDB(datosRetorno)
            Toast.makeText(applicationContext, "Guardando información", Toast.LENGTH_SHORT).show()
            finish()

        }

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