package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetRegistroFamiliarById
import com.example.electrorui.usecase.UpdateFamiliarUC
import com.example.electrorui.usecase.model.RegistroFamilias
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamiliarMod_AVM @Inject constructor(
    private val getRegistroFamiliarById: GetRegistroFamiliarById,
    private val getAllPaisesUC: GetAllPaisesUC,
    private val updateFamiliarUC: UpdateFamiliarUC,
) : ViewModel(){

    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val dataRegistro by lazy { MutableLiveData<RegistroFamilias>() }

    fun onCreate(id : Int){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }
            dataRegistro.value = getRegistroFamiliarById(id)
        }
    }

    fun updateFamiliarDB(registro : RegistroFamilias){
        viewModelScope.launch {
            updateFamiliarUC(registro)
        }
    }
}