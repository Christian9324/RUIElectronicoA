package com.example.electrorui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.electrorui.databinding.ActivityRegistroBinding
import com.example.electrorui.networkApi.model.RegistroNacionalidadModel
import com.example.electrorui.ui.fragments.CapturaFragment
import com.example.electrorui.ui.viewModel.RegistroNuevo_AVM
import com.example.electrorui.ui.viewModel.Registro_AVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistroActivity : AppCompatActivity() {

    companion object{
        val EXTRA_REGISTRO = "RegistroActivity:registro"
    }

    private lateinit var binding : ActivityRegistroBinding
    private val dataActivityViewM : Registro_AVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataActivityViewM.onCreated()

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

        var nacio = emptyArray<String>()
        val spinnerArrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nacio)
        binding.spinnerPAIS.adapter = spinnerArrayAdapter

        dataActivityViewM.paises.observe(this){
            spinnerArrayAdapter.clear()
            spinnerArrayAdapter.addAll(it)
            spinnerArrayAdapter.notifyDataSetChanged()
        }

        binding.buttonGuardarForm.setOnClickListener {

            val nacionalidad = binding.spinnerPAIS.selectedItem.toString()
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

            val datosRetorno = RegistroNacionalidadModel(
                nacionalidad,
                "",
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

            val datosRetornoIntent = Intent()
            datosRetornoIntent.putExtra(CapturaFragment.EXTRA_REGISTRO, datosRetorno)
            setResult(RESULT_OK, datosRetornoIntent)
            Toast.makeText(this, "Guardando datos", Toast.LENGTH_LONG).show()
            finish()
        }

        val registro = intent.getParcelableExtra<RegistroNacionalidadModel>(EXTRA_REGISTRO)

        if (registro != null){
            binding.spinnerPAIS.setSelection(spinnerArrayAdapter.getPosition("${registro.nacionalidad}"))

            binding.editTextASHombres.setText(registro.AS_hombres.toString())
            binding.editTextASMujeresNoEmb.setText(registro.AS_mujeresNoEmb.toString())
            binding.editTextASMujeresEmb.setText(registro.AS_mujeresEmb.toString())

            binding.editTextNucleosFam.setText(registro.nucleosFamiliares.toString())

            binding.editTextAANNAsHombres.setText(registro.AA_NNAs_hombres.toString())
            binding.editTextAANNAsMujeresNoEmb.setText(registro.AA_NNAs_mujeresNoEmb.toString())
            binding.editTextAANNAsMujeresEmb.setText(registro.AA_NNAs_mujeresEmb.toString())

            binding.editTextNNAsAHombres.setText(registro.NNAsA_hombres.toString())
            binding.editTextNNAsAMujeresNoEmb.setText(registro.NNAsA_mujeresNoEmb.toString())
            binding.editTextNNAsAMujeresEmb.setText(registro.NNAsA_mujeresEmb.toString())

            binding.editTextNNAsSHombres.setText(registro.NNAsS_hombres.toString())
            binding.editTextNNAsSMujeresNoEmb.setText(registro.NNAsS_mujeresNoEmb.toString())
            binding.editTextNNAsSMujeresEmb.setText(registro.NNAsS_mujeresEmb.toString())
        }
    }

    fun verificarDatoInt( dato : String) : Int{
        if (dato == ""){
            return 0
        }else
            return Integer.parseInt(dato)
    }
}