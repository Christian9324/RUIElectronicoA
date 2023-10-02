package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class GetAeropuertos @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(): List<String> {
        val aeropuertos = repository.getPuntosAeropuertoDB()
        return if (!aeropuertos.isNullOrEmpty()){
            aeropuertos
        } else
            listOf("")
    }
}