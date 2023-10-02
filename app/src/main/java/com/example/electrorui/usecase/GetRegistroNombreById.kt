package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNombres
import javax.inject.Inject

class GetRegistroNombreById @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(id : Int): RegistroNombres {
        val numFam = repository.getNombreByIdFromDB(id)
        return if (!listOf(numFam).isEmpty()){
            numFam
        } else
            RegistroNombres(0,"", "", "", "", "", "", false, false)
    }
}