package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class GetInfoMasivo @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(): Int {
        val totalRFam = repository.getTotalRegistrosNombresFromDB()
        val totalRNom = repository.getTotalRegistrosFamiliasFromDB()
        return (totalRFam + totalRNom)
    }
}