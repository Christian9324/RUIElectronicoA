package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.RescateComp
import javax.inject.Inject

class SetDatosPendientesAPI @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke() {
        val registrosConteo = repository.getAllDataConteoRapidoFromDB()
        val registrosCompleto = repository.getAllRescateCompletoFromDB()
        try {
            if ( !registrosConteo.isNullOrEmpty()) {
//                Log.e("info Conteo Api", "Entro a funcion")
                repository.insertConteosFromApi(registrosConteo)
                repository.deleteAllDataConteoRapidoFromDB()
            }
            if ( !registrosCompleto.isNullOrEmpty()) {
//                Log.e("info Rescate Api", "Entro a funcion")
                repository.insertRescatesFromApi(registrosCompleto)
                repository.deleteAllRescateCompletoFromDB()
            }
        }catch (e : Exception){
            Log.e("error al insertar API", e.toString())
        }
    }
}