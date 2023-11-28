package com.example.electrorui.ui

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.electrorui.databinding.ActivityConteoRactivityBinding
import com.example.electrorui.databinding.ActivityPopupCorreccionesBinding
import com.example.electrorui.db.PrefManager
import com.example.electrorui.ui.adapters.NacionalidadesAdapter
import com.example.electrorui.ui.viewModel.ConteoR_AVM
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConteoRActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConteoRactivityBinding
    private lateinit var nacionalidadesAdapter : NacionalidadesAdapter

    private lateinit var prefManager: PrefManager
    private var isConnected : Boolean = false

    private val dataActivityViewM : ConteoR_AVM by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConteoRactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        prefManager = PrefManager(this)
        isConnected = prefManager.getConnection()!!

        dataActivityViewM.onCreate()

        dataActivityViewM.masivo.observe(this){
            if(it){
                binding.tvRescateMasivo.visibility = View.VISIBLE
            } else {
                binding.tvRescateMasivo.visibility = View.GONE
            }
        }

        nacionalidadesAdapter = NacionalidadesAdapter(emptyList())
        { data, pos ->
//            Toast.makeText(this, data.nacionalidad.toString(), Toast.LENGTH_LONG).show()
            popUpConteoR(data, pos)
        }
        binding.recyclerPais.adapter = nacionalidadesAdapter

        dataActivityViewM.registros.observe(this){
            nacionalidadesAdapter.registroNacionalidad = it
            nacionalidadesAdapter.notifyDataSetChanged()
        }

        binding.btnNcionalidad.setOnClickListener {
            val intentRegistro = Intent(this, RegistroNuevoActivity::class.java)
//            activityResultLauncher.launch(intentRegistro)
            startActivity(intentRegistro)
        }

        binding.btnEnviar.setOnClickListener {

            if(isConnected){
                dataActivityViewM.enviarAPI()
            } else{
                dataActivityViewM.enviarDB()
            }

            dataActivityViewM.deleteAll()

            object : CountDownTimer(1000, 100){
                override fun onTick(p0: Long) {
                    binding.pbEnvirarConteo.visibility = View.VISIBLE
                }

                override fun onFinish() {
                    binding.pbEnvirarConteo.visibility = View.GONE
                    finish()
                }

            }.start()

        }

    }

    private fun popUpConteoR(data: RegistroNacionalidad, pos: Int) {
        var bindingC = ActivityPopupCorreccionesBinding.inflate(layoutInflater)
        var dialogC = Dialog(this)
        dialogC.setCancelable(true)
        dialogC.setContentView(bindingC.root)

        bindingC.btnEliminar.setOnClickListener {

            val dataToDel = RegistroNacionalidad(
                pos,
                data.nacionalidad, data.iso3,
                data.AS_hombres, data.AS_mujeresNoEmb, data.AS_mujeresEmb,
                data.nucleosFamiliares,
                data.AA_NNAs_hombres, data.AA_NNAs_mujeresNoEmb, data.AA_NNAs_mujeresEmb,
                data.NNAsA_hombres, data.NNAsA_mujeresNoEmb, data.NNAsA_mujeresEmb,
                data.NNAsS_hombres, data.NNAsS_mujeresNoEmb, data.NNAsS_mujeresEmb,
                )

            dataActivityViewM.delRegConteoR(dataToDel)
            Toast.makeText(this, "Elemento Eliminado", Toast.LENGTH_SHORT).show()

            dataActivityViewM.onCreate()

            dialogC.dismiss()
        }

        bindingC.btnCorreccion.setOnClickListener {
            val intentConteoR = Intent(this, RegistroNuevoModActivity::class.java)
            intentConteoR.putExtra(
                RegistroNuevoModActivity.EXTRA_IDCONTEO_DB,
                pos
            )
            startActivity(intentConteoR)

            dialogC.dismiss()
        }

        dialogC.show()
    }

    override fun onResume() {
        super.onResume()
        dataActivityViewM.onCreate()
    }
}