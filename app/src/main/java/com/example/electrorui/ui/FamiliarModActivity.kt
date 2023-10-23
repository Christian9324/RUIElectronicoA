package com.example.electrorui.ui

import android.R
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.display.DisplayManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.getSystemService
import androidx.core.graphics.drawable.DrawableCompat
import com.example.electrorui.databinding.ActivityFamiliarModBinding
import com.example.electrorui.databinding.ToastLayoutErrorBinding
import com.example.electrorui.ui.viewModel.FamiliarMod_AVM
import com.example.electrorui.usecase.model.RegistroFamilias
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.format.DateTimeFormatter
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class FamiliarModActivity : AppCompatActivity() {

    companion object{
        val EXTRA_IDFAMILIA_DB = "FamiliarModActivity:IdFam"
    }

    private lateinit var binding : ActivityFamiliarModBinding
    private val dataActivityViewM : FamiliarMod_AVM by viewModels()
    private lateinit var icon : Drawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamiliarModBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AndroidThreeTen.init(this)

        val adapterSpinner = ArrayAdapter.createFromResource(
            this, com.example.electrorui.R.array.tipo_parentesco, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerParentescoF.adapter = adapterSpinner

        icon = AppCompatResources.getDrawable(this, com.example.electrorui.R.drawable.ic_error_24)!!
        DrawableCompat.setTint(icon, resources.getColor(com.example.electrorui.R.color.rojo))
        icon.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        binding.checkHombre.isChecked = true

        val nacio  = emptyList<String>()

        var autocompleteArrayAdapter = ArrayAdapter<String>(this, R.layout.simple_spinner_dropdown_item, nacio)
        binding.spinnerPAISF.threshold = 1
        binding.spinnerPAISF.setAdapter(autocompleteArrayAdapter)
        dataActivityViewM.paises.observe(this){
            binding.spinnerPAISF.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it))
            autocompleteArrayAdapter.notifyDataSetChanged()
        }

        binding.checkHombre.setOnClickListener {
            binding.checkHombre.isChecked = true
            binding.checkMujer.isChecked = false

            binding.LLEmbarazado.visibility = View.GONE
            binding.checkEmbarazada.isChecked = false
        }

        binding.checkMujer.setOnClickListener {
            binding.checkHombre.isChecked = false
            binding.checkMujer.isChecked = true

            binding.LLEmbarazado.visibility = View.VISIBLE
        }

        dataActivityViewM.dataRegistro.observe(this){
            binding.spinnerPAISF.setText(it.nacionalidad)
            binding.etNombreF.setText(it.nombre)
            binding.etApellidosF.setText(it.apellidos)
            binding.etNoIdentidadF.setText(it.noIdentidad)
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
            binding.spinnerParentescoF.setSelection(getIndex( binding.spinnerParentescoF , it.parentesco))
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

        binding.btnGuardar.setOnClickListener {

            val nacionalidad = binding.spinnerPAISF.text.toString()
            val iso3D = dataActivityViewM.iso3.value
            val nom = binding.etNombreF.text.toString().uppercase()
            val apellidos = binding.etApellidosF.text.toString().uppercase()
            val noIdentidad = binding.etNoIdentidadF.text.toString()
            val parentesco = binding.spinnerParentescoF.selectedItem.toString().uppercase()
            val sexo : Boolean = binding.checkHombre.isChecked
            val numeroFamilia = intent.getIntExtra(FamiliarActivity.EXTRA_FAMILIA, 1)

            val paises = dataActivityViewM.paises.value
            val indexNacionalidad = paises?.indexOf(nacionalidad)



            if(nacionalidad.isNullOrEmpty()){
                binding.spinnerPAISF.setError("LLENAR PARA CONTINUAR", icon)
                binding.spinnerPAISF.requestFocus()
            } else {
                if (nom.isNullOrEmpty()){
                    binding.etNombreF.setError("LLENAR PARA CONTINUAR", icon)
                    binding.etNombreF.requestFocus()
                } else {
                    binding.etNombreF.error = null
                    if (apellidos.isNullOrEmpty()) {
                        binding.etApellidosF.setError("LLENAR PARA CONTINUAR", icon)
                        binding.etApellidosF.requestFocus()
                    } else {
                        binding.etApellidosF.error = null
                        if (binding.spinnerParentescoF.selectedItemPosition == 0) {
                            binding.spinnerParentescoIcon.visibility = View.VISIBLE
                            showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                        } else {
                            binding.spinnerParentescoIcon.visibility = View.GONE
                            if (binding.ldFechaNacimiento.localDate == null) {
                                binding.fechaNacIcon.visibility = View.VISIBLE
                                showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                            } else {
                                binding.fechaNacIcon.visibility = View.GONE

                                val fechaNacimiento = binding.ldFechaNacimiento.localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                val fechaNacimientoDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento)
                                val fechaActual = Date(System.currentTimeMillis())
                                val diferencia = fechaActual.time - fechaNacimientoDate?.time!!
                                val edad : Float = diferencia.toFloat() / (31536000000)
                                val embarazada = binding.checkEmbarazada.isChecked

                                val datosRetorno = RegistroFamilias(
                                    intent.getIntExtra(EXTRA_IDFAMILIA_DB, 1),
                                    nacionalidad,
                                    iso3D!![indexNacionalidad!!],
                                    nom,
                                    apellidos,
                                    noIdentidad,
                                    parentesco,
                                    fechaNacimiento,
                                    edad > 18,
                                    sexo,
                                    embarazada,
                                    numeroFamilia,
                                )

                                dataActivityViewM.updateFamiliarDB(datosRetorno)
                                Toast.makeText(applicationContext, "Actualizando información", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }
                }
            }
        }
        dataActivityViewM.onCreate(intent.getIntExtra(EXTRA_IDFAMILIA_DB, 1))
    }

    fun showToastError(texto : String, duracion : Int){

        val displaySize = DisplayMetrics()
        getSystemService<DisplayManager>()?.getDisplay(Display.DEFAULT_DISPLAY)?.getMetrics(displaySize)

        val bindingToast = ToastLayoutErrorBinding.inflate(layoutInflater)
        bindingToast.textview.setText(texto)
        val toast = Toast(this)
        toast.view = bindingToast.root
        toast.duration = duracion
        toast.setGravity(Gravity.BOTTOM, 0, (displaySize.heightPixels / 2) - 20)
//        displaySize.heightPixels
        toast.show()

    }

    private fun getIndex(spinnerParentescoF: Spinner, parentesco: String): Int {
        for(i in 0..spinnerParentescoF.count){
            if (spinnerParentescoF.getItemAtPosition(i).toString().equals(parentesco, ignoreCase = true)){
                return i
            }
        }
        return 0
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}