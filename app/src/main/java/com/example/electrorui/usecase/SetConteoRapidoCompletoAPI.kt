package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.RescateComp
import javax.inject.Inject

class SetConteoRapidoCompletoAPI @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke() {
        val registros = repository.getAllDataConteoRapidoFromDB()
        try {
            if ( !registros.isNullOrEmpty()) {
                repository.insertConteosFromApi(registros)
                repository.deleteAllDataConteoRapidoFromDB()
            }
        }catch (e : Exception){
            Log.e("error al insertar API", e.toString())
        }
    }
}