package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Fuerza
import javax.inject.Inject

class GetFuerzaByOrUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(oficinaR : String): List<Fuerza> {

        return repository.getFuerzaByOrDB(oficinaR)

    }
}