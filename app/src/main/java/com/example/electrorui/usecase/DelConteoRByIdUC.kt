package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import javax.inject.Inject

class DelConteoRByIdUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(item : RegistroNacionalidad) {
        repository.deleteConteoRByIdFromDB(item)
    }
}