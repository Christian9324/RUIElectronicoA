package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.RegistroFamilias
import javax.inject.Inject

class DelFamiliarByIdUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(item : RegistroFamilias) {
        repository.deleteRegistroFamiliasIdFromDB(item)
    }
}