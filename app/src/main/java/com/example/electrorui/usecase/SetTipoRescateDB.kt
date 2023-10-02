package com.example.electrorui.usecase

//import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Mensaje
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.Rescate
import javax.inject.Inject

class SetTipoRescateDB @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(registros: List<Rescate>) {
        repository.insertTipoRescateToDB(registros)
    }
}