package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.PinFamilias
import com.example.electrorui.usecase.model.PinNacionalidad
import javax.inject.Inject

class GetDataPinFamilias @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): List<PinFamilias> {
        val listPinNombres = repository.getDataPinFamiliasFromDB()

        return listPinNombres
    }
}