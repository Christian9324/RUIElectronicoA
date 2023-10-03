package com.example.electrorui.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.electrorui.databinding.ActivityNombresBinding
import com.example.electrorui.ui.fragments.DatePickerFragment
import com.example.electrorui.ui.viewModel.Nombres_AVM
import com.example.electrorui.usecase.model.RegistroNombres
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class NombresActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NACIONALIDAD = "NombresActivity:nacionalidad"
    }


    private lateinit var binding: ActivityNombresBinding
    private val dataActivityViewM : Nombres_AVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNombresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        val nacio  = emptyList<String>()

        val nacionalidadPadre = intent.getStringExtra(EXTRA_NACIONALIDAD)
        binding.spinnerPAIS.setText(nacionalidadPadre)

        binding.checkHombre.isChecked = true

        var autocompleteArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nacio)
        binding.spinnerPAIS.threshold = 1
        binding.spinnerPAIS.setAdapter(autocompleteArrayAdapter)

        dataActivityViewM.paises.observe(this){
            binding.spinnerPAIS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it))
            autocompleteArrayAdapter.notifyDataSetChanged()
        }

        binding.checkHombre.setOnClickListener {
            binding.checkHombre.isChecked = true
            binding.checkMujer.isChecked = false
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

        binding.checkMujer.setOnClickListener {
            binding.checkHombre.isChecked = false
            binding.checkMujer.isChecked = true
        }

        binding.btnGuardar.setOnClickListener {

            val nacionalidad = binding.spinnerPAIS.text.toString()
            val iso3D = dataActivityViewM.iso3.value
            val nom = binding.etNombre.text.toString()
            val apellidos = binding.etApellidos.text.toString()
            val noIdentidad = binding.etNoIdentidad.text.toString()
            val fechaNacimiento = binding.ldFechaNacimiento.localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val sexo : Boolean = binding.checkHombre.isChecked

            val paises = dataActivityViewM.paises.value
            val indexNacionalidad = paises?.indexOf(nacionalidad)

            val fechaNacimientoDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento)
            val fechaActual = Date(System.currentTimeMillis())
            val diferencia = fechaActual.time - fechaNacimientoDate?.time!!
            val edad : Float = diferencia.toFloat() / (31536000000)

            val datosRetorno = RegistroNombres(
                0,
                nacionalidad,
                iso3D!![indexNacionalidad!!],
                nom,
                apellidos,
                noIdentidad,
                fechaNacimiento,
                edad > 18,
                sexo,
            )

            dataActivityViewM.saveToDB(datosRetorno)
            Toast.makeText(applicationContext, "Guardando información", Toast.LENGTH_SHORT).show()
            finish()
        }

        dataActivityViewM.onCreate()

    }

//    private fun ShowDatePickerDialog() {
//        val datePickerDialog = DatePickerFragment { date -> onDateSelected(date)}
//        datePickerDialog.show(supportFragmentManager, "date")
//    }
//
//    private fun onDateSelected(date: String) {
//        binding.etFechaNacimiento.setText(date)
//    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}