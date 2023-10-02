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
        val registrosC = repository.getAllRescateCompletoFromDB()
        try {
            if ( !registrosConteo.isNullOrEmpty()) {
                repository.insertConteosFromApi(registrosConteo)
                repository.deleteAllDataConteoRapidoFromDB()
            }
            if ( !registrosC.isNullOrEmpty()) {
                repository.insertRescatesCompletos(registrosC)
                repository.deleteAllRescateCompletoFromDB()
            }
        }catch (e : Exception){
            Log.e("error al insertar API", e.toString())
        }
    }
}