package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Pais
import com.example.electrorui.usecase.model.RegistroNacionalidad
import javax.inject.Inject

class GetAllRegistrosConteoUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): List<RegistroNacionalidad> {
        val datosConteoRapido = repository.getAllDatosRegistroDB()

        return datosConteoRapido
    }

}