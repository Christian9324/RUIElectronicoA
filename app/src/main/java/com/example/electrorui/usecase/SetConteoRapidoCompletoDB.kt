package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.RescateComp
import javax.inject.Inject

class SetConteoRapidoCompletoDB @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(registo : List<ConteoRapidoComp>) {
        if ( !registo.isNullOrEmpty()){
            val validUser = repository.insertDataConteoRapidoToDB(registo)
        }
    }
}