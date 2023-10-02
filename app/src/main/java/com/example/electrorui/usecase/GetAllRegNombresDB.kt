package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.RegistroNombres
import javax.inject.Inject

class GetAllRegNombresDB @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke(): List<RegistroNombres> {
        val registrosN = repository.getAllRegistroNombresDB()
        return if (!registrosN.isNullOrEmpty()){
            registrosN
        } else
            emptyList<RegistroNombres>()
    }
}