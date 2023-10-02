package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class DelAllFamiliasUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke() {

        repository.deleteAllRegistrosFamilias()

    }
}