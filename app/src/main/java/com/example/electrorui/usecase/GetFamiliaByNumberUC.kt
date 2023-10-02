package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.UsuarioEntity
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RegistroNombres
import com.example.electrorui.usecase.model.User
import javax.inject.Inject

class GetFamiliaByNumberUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(numero: Int): List<RegistroFamilias> {
        return repository.getFamiliaByNumDB(numero)
    }
}