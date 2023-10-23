package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.RegistroFamilias
import javax.inject.Inject

class GetRegistroFamiliarById @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(id : Int): RegistroFamilias {
        val numFam = repository.getFamiliarByIdFromDB(id)
        return if (!listOf(numFam).isEmpty()){
            numFam
        } else
            RegistroFamilias(0,"", "", "", "", "", "", "", false, false, false, 0)
    }
}