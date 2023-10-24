package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.DelAllRegistrosUC
import com.example.electrorui.usecase.DelFamiliarByIdUC
import com.example.electrorui.usecase.DelNombreByIdUC
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetAllRegistrosConteoUC
import com.example.electrorui.usecase.GetRegNombresByNacioUC
import com.example.electrorui.usecase.SetEntryRegistrosUC
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RescatesNombres_AVM @Inject constructor(
    private val getAllPaisesUC: GetAllPaisesUC,
    private val getRegNombresByNacioUC: GetRegNombresByNacioUC,
    private val delRegNomById : DelNombreByIdUC,

    ) : ViewModel(){

    val paisPadre by lazy { MutableLiveData<String>() }
    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val datosNombres by lazy { MutableLiveData<List<RegistroNombres>>() }

    fun onCreate(){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }

            if (paisPadre.value.isNullOrEmpty()){

            } else {
                datosNombres.value = getRegNombresByNacioUC(paisPadre.value.toString())
            }

        }
    }

    fun delRegNombre(dataToDel: RegistroNombres) {
        viewModelScope.launch {
            delRegNomById(dataToDel)
        }
    }

}