package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Pais
import javax.inject.Inject

class GetAllPaisesInitUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): List<Pais> {
        val listPaises = repository.getAllPaisesFromApi()

        return if (!listPaises.isNullOrEmpty()){
            repository.deleteAllPaises()
            repository.insertPaises(listPaises)
            listPaises
        } else{
            val DBPais = repository.getAllPaisesFromDB()
            if(!DBPais.isNullOrEmpty()){
                DBPais
            } else {
                val nullPais = Pais("", "")
                listOf(nullPais)
            }
        }
    }
}