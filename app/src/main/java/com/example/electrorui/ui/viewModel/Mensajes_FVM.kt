package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.DelAllRegistrosUC
import com.example.electrorui.usecase.GetAllMensajesDB
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetAllRegistrosConteoUC
import com.example.electrorui.usecase.SetEntryRegistrosUC
import com.example.electrorui.usecase.SetRegistrosNombreUC
import com.example.electrorui.usecase.model.Mensaje
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Mensajes_FVM @Inject constructor(
    private val getAllPaisesUC: GetAllPaisesUC,
    private val setRegistrosNombreUC: SetRegistrosNombreUC,
    private val getAllMensajesDB: GetAllMensajesDB,

) : ViewModel(){

    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val datosMensajes by lazy { MutableLiveData<List<Mensaje>>() }

    fun onCreate(){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }
            datosMensajes.value = getAllMensajesDB()
        }
    }

    fun saveToDB(registroNombre: RegistroNombres){
        viewModelScope.launch {
            setRegistrosNombreUC(
                listOf(registroNombre)
            )
        }
    }

}