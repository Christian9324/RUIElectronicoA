package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetConteoRByIdUC
import com.example.electrorui.usecase.UpdateConteoRUC
import com.example.electrorui.usecase.model.RegistroNacionalidad
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroNuevoMod_AVM @Inject constructor(
    private val getAllPaisesUC: GetAllPaisesUC,
    private val getRegistroByIdConteo: GetConteoRByIdUC,
    private val updateEntryRegistrosUC: UpdateConteoRUC,

    ) : ViewModel(){

    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val datosConteoR by lazy { MutableLiveData<RegistroNacionalidad>() }

    fun onCreate(id : Int){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }

            datosConteoR.value = getRegistroByIdConteo(id)
        }
    }

    fun updateToDB(registroNacionalidad: RegistroNacionalidad){
        viewModelScope.launch {
            updateEntryRegistrosUC(
                registroNacionalidad
            )
        }
    }

}