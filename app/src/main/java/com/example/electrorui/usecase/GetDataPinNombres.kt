package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.PinNacionalidad
import javax.inject.Inject

class GetDataPinNombres @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): List<PinNacionalidad> {
        val listPinNombres = repository.getDataPinNombresFromDB()

        return listPinNombres
    }
}