package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toPaisDB
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class GetAllOrs @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(): List<String> {

        val listEstadosR = repository.getAllOrsDB()

        return if (!listEstadosR.isNullOrEmpty()){
            listEstadosR
        } else{
            val EmptyEstadosR = ""
            listOf(EmptyEstadosR)
        }
    }
}