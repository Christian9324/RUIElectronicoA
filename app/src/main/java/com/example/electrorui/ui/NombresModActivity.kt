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
import com.example.electrorui.databinding.ActivityNombresModBinding
import com.example.electrorui.ui.viewModel.NombresMod_AVM
import com.example.electrorui.usecase.model.RegistroNombres
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class NombresModActivity : AppCompatActivity() {

    companion object{
        val EXTRA_IDNOMBRE_DB = "NombresModActivity:IdNom"
    }

    private lateinit var binding : ActivityNombresModBinding
    private val dataActivityViewM : NombresMod_AVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNombresModBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        val nacio  = emptyList<String>()

        var autocompleteArrayAdapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, nacio)
        binding.spinnerPAIS.threshold = 1
        binding.spinnerPAIS.setAdapter(autocompleteArrayAdapter)
        dataActivityViewM.paises.observe(this){
            binding.spinnerPAIS.setAdapter(ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, it))
            autocompleteArrayAdapter.notifyDataSetChanged()
        }

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

        binding.ldFechaNacimiento.setOnLocalDatePickListener {date ->
            val fecha = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            hideKeyboard()

        }

        dataActivityViewM.dataRegistro.observe(this){
            binding.spinnerPAIS.setText(it.nacionalidad)
            binding.etNombre.setText(it.nombre)
            binding.etApellidos.setText(it.apellidos)
            binding.etNoIdentidad.setText(it.noIdentidad)
//            binding.etFechaNacimiento.setText(it.fechaNacimiento)
            binding.ldFechaNacimiento.localDate =
                org.threeten.bp.LocalDate.parse(
                    it.fechaNacimiento,
                    DateTimeFormatter.ofPattern("dd/MM/yyyy")
                )
            if (it.sexo){
                binding.checkHombre.isChecked = true
                binding.checkMujer.isChecked = false
            } else {
                binding.checkHombre.isChecked = false
                binding.checkMujer.isChecked = true
            }
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
                intent.getIntExtra(EXTRA_IDNOMBRE_DB, 1),
                nacionalidad,
                iso3D!![indexNacionalidad!!],
                nom,
                apellidos,
                noIdentidad,
                fechaNacimiento,
                edad > 18,
                sexo,
            )

            dataActivityViewM.updateNombresDB(datosRetorno)
            Toast.makeText(applicationContext, "Actualizando información", Toast.LENGTH_SHORT).show()
            finish()
        }

        dataActivityViewM.onCreate(intent.getIntExtra(EXTRA_IDNOMBRE_DB, 1))

    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}