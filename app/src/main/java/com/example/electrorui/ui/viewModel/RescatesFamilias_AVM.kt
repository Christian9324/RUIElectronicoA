package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.DelAllRegistrosUC
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetAllRegistrosConteoUC
import com.example.electrorui.usecase.GetFamiliaByNumberUC
import com.example.electrorui.usecase.GetRegNombresByNacioUC
import com.example.electrorui.usecase.GetTotalFamilias
import com.example.electrorui.usecase.SetEntryRegistrosUC
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescatesFamilias_AVM @Inject constructor(
    private val getAllPaisesUC: GetAllPaisesUC,
    private val getFamiliaByNumberUC: GetFamiliaByNumberUC,
    private val getTotalFamilias: GetTotalFamilias,

) : ViewModel(){

    val numFamilia by lazy { MutableLiveData<Int>() }
    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val datosFamilia by lazy { MutableLiveData<List<RegistroFamilias>>() }

    fun onCreate(numFamilia : Int){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }

            datosFamilia.value = getFamiliaByNumberUC(numFamilia)

//            if (paisPadre.value.isNullOrEmpty()){
//
//            } else {
//                datosNombres.value = getRegNombresByNacioUC(paisPadre.value.toString())
//            }

        }
    }

}