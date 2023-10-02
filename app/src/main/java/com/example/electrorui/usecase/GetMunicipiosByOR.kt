package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Municipios
import javax.inject.Inject

class GetMunicipiosByOR @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(oficinaR : String): List<Municipios> {

        return repository.getMunByOrDB(oficinaR)

    }
}