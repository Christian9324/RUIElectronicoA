package com.example.electrorui.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.electrorui.databinding.ActivityRescateFamiliasBinding
import com.example.electrorui.ui.adapters.RescateFamiliaAdapter
import com.example.electrorui.ui.viewModel.RescatesFamilias_AVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RescateFamiliasActivity : AppCompatActivity() {

    companion object{
        val EXTRA_NOM_FAMILIA = "RescateFamiliasActivity:NoFamilia"
    }

    private lateinit var binding : ActivityRescateFamiliasBinding
    private lateinit var familiasAdapter : RescateFamiliaAdapter
    private val dataActivityViewM : RescatesFamilias_AVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRescateFamiliasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        familiasAdapter = RescateFamiliaAdapter(emptyList()){
                data , pos ->
            val intentRegistroFamilias = Intent(this, FamiliarModActivity::class.java)
            intentRegistroFamilias.putExtra(
                FamiliarModActivity.EXTRA_IDFAMILIA_DB,
                pos
            )
            startActivity(intentRegistroFamilias)

        }

        binding.RecyclerFamilias.adapter = familiasAdapter

        dataActivityViewM.datosFamilia.observe(this){
            familiasAdapter.registroFamilias = it
            familiasAdapter.notifyDataSetChanged()
        }

        binding.btnAddFamiliar.setOnClickListener {
            val intentRegistroNombres = Intent(this, FamiliarActivity::class.java)
            intentRegistroNombres.putExtra(
                FamiliarActivity.EXTRA_FAMILIA,
                intent.getIntExtra(EXTRA_NOM_FAMILIA,
                    1)
            )
            startActivity(intentRegistroNombres)
        }

        binding.btnGuardar.setOnClickListener {
            finish()
        }

        dataActivityViewM.onCreate( intent.getIntExtra(EXTRA_NOM_FAMILIA,1) )

    }

    override fun onResume() {
        super.onResume()
        dataActivityViewM.onCreate( intent.getIntExtra(EXTRA_NOM_FAMILIA,1) )
    }
}