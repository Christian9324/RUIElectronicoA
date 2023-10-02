package com.example.electrorui.usecase

//import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.RegistroNacionalidad
import javax.inject.Inject

class SetEntryRegistrosUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(registros: List<RegistroNacionalidad>) {

        if (!registros.isNullOrEmpty()){
            repository.insertRegistroNacionalidad(registros)
        } else{

        }
    }
}