package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.RegistroFamilias
import javax.inject.Inject

class GetAllFamiliasDB @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(): List<RegistroFamilias> {
        val registrosFam = repository.getAllRegistrosFamiliasDB()
        return if (!registrosFam.isNullOrEmpty()){
            registrosFam
        } else
            emptyList<RegistroFamilias>()
    }
}