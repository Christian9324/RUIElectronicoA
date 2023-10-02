package com.example.electrorui.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.electrorui.databinding.ActivityRegistroNuevoBinding
import com.example.electrorui.ui.viewModel.RegistroNuevo_AVM
import com.example.electrorui.usecase.model.RegistroNacionalidad
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroNuevoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroNuevoBinding
    private val dataActivityViewM : RegistroNuevo_AVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroNuevoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val nacio  = emptyList<String>()

        binding.editTextNucleosFam.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val numS = p0.toString()
                if(!numS.isEmpty()){
                    if (numS.toInt() > 0){
                        binding.LLFamiliares.visibility = View.VISIBLE
                    } else if(numS.toInt() == 0){
                        binding.LLFamiliares.visibility = View.GONE
                    }
                }
            }
        })

        var autocompleteArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, nacio)
        binding.spinnerPAIS.threshold = 2
        binding.spinnerPAIS.setAdapter(autocompleteArrayAdapter)

        dataActivityViewM.paises.observe(this){
            binding.spinnerPAIS.setAdapter(ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, it))
            autocompleteArrayAdapter.notifyDataSetChanged()
        }

        binding.buttonGuardarForm.setOnClickListener {

            val iso3D = dataActivityViewM.iso3.value
            val paises = dataActivityViewM.paises.value

            val nacionalidad = binding.spinnerPAIS.text.toString()
            val AS_hombres = (binding.editTextASHombres.text.toString())
            val AS_mujeresNoEmb = (binding.editTextASMujeresNoEmb.text.toString())
            val AS_mujeresEmb = (binding.editTextASMujeresEmb.text.toString())

            val nucleosFamiliares = (binding.editTextNucleosFam.text.toString())

            val AA_NNAs_hombres = (binding.editTextAANNAsHombres.text.toString())
            val AA_NNAs_mujeresNoEmb = (binding.editTextAANNAsMujeresNoEmb.text.toString())
            val AA_NNAs_mujeresEmb = (binding.editTextAANNAsMujeresEmb.text.toString())

            val NNAsA_hombres = (binding.editTextNNAsAHombres.text.toString())
            val NNAsA_mujeresNoEmb = (binding.editTextNNAsAMujeresNoEmb.text.toString())
            val NNAsA_mujeresEmb = (binding.editTextNNAsAMujeresEmb.text.toString())

            val NNAsS_hombres = (binding.editTextNNAsSHombres.text.toString())
            val NNAsS_mujeresNoEmb = (binding.editTextNNAsSMujeresNoEmb.text.toString())
            val NNAsS_mujeresEmb = (binding.editTextNNAsSMujeresEmb.text.toString())

            val indexNacionalidad = paises?.indexOf(nacionalidad)

            val datosRetorno = RegistroNacionalidad(
                nacionalidad,
                iso3D!![indexNacionalidad!!],
                verificarDatoInt(AS_hombres),
                verificarDatoInt(AS_mujeresNoEmb),
                verificarDatoInt(AS_mujeresEmb),
                verificarDatoInt(nucleosFamiliares),
                verificarDatoInt(AA_NNAs_hombres),
                verificarDatoInt(AA_NNAs_mujeresNoEmb),
                verificarDatoInt(AA_NNAs_mujeresEmb),
                verificarDatoInt(NNAsA_hombres),
                verificarDatoInt(NNAsA_mujeresNoEmb),
                verificarDatoInt(NNAsA_mujeresEmb),
                verificarDatoInt(NNAsS_hombres),
                verificarDatoInt(NNAsS_mujeresNoEmb),
                verificarDatoInt(NNAsS_mujeresEmb),
            )

            dataActivityViewM.saveToDB(datosRetorno)
//            val datosRetornoIntent = Intent()
//            datosRetornoIntent.putExtra(CapturaFragment.EXTRA_REGISTRO_NUEVO, datosRetorno)
//            setResult(RESULT_OK, datosRetornoIntent)

            Toast.makeText(this, "Guardando datos", Toast.LENGTH_SHORT).show()
            finish()
        }

        dataActivityViewM.onCreate()
    }

    fun verificarDatoInt( dato : String) : Int{
        if (dato == ""){
            return 0
        }else
            return Integer.parseInt(dato)
    }
}