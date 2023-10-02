package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class GetTotalFamilias @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(): Int {
        val numFam = repository.getNumFamiliasDB()
        return if (!listOf(numFam).isEmpty()){
            numFam
        } else
            0
    }
}