package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.UsuarioEntity
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import com.example.electrorui.usecase.model.User
import javax.inject.Inject

class GetRegNombresByNacioUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(nacionalidad: String): List<RegistroNombres> {

        return repository.getAllByNacionalidadFromDB(nacionalidad)

    }
}