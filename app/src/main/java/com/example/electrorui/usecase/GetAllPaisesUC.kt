package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class GetAllPaisesUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): List<Pais> {

        val DBPais = repository.getAllPaisesFromDB()

        return if(!DBPais.isNullOrEmpty()){
            DBPais
        } else {
            val nullPais = Pais("", "")
            listOf(nullPais)
        }
    }
}