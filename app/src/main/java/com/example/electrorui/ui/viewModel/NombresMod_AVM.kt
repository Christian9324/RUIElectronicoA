package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetRegistroFamiliarById
import com.example.electrorui.usecase.GetRegistroNombreById
import com.example.electrorui.usecase.UpdateFamiliarUC
import com.example.electrorui.usecase.UpdateNombresUC
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNombres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NombresMod_AVM @Inject constructor(
    private val getRegistroNombreById: GetRegistroNombreById,
    private val getAllPaisesUC: GetAllPaisesUC,
    private val updateNombresUC: UpdateNombresUC,
) : ViewModel(){

    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val dataRegistro by lazy { MutableLiveData<RegistroNombres>() }

    fun onCreate(id : Int){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }
            dataRegistro.value = getRegistroNombreById(id)
        }
    }

    fun updateNombresDB(registro : RegistroNombres){
        viewModelScope.launch {
            updateNombresUC(registro)
        }
    }
}