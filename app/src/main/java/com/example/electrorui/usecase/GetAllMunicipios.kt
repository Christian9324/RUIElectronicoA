package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Municipios
import javax.inject.Inject

class GetAllMunicipios @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): List<Municipios> {
        val listMunicipios = repository.getAllMunicipiosFromApi()

        return if (!listMunicipios.isNullOrEmpty()){
            repository.deleteAllMunicipios()
            repository.insertMunicipios(listMunicipios)
            listMunicipios
        } else{
            val DBMunicipios = repository.getAllMunicipiosFromDB()
            if(!DBMunicipios.isNullOrEmpty()){
                DBMunicipios
            } else {
                val nullFuerza = Municipios("", "", "",)
                listOf(nullFuerza)
            }
        }
    }
}