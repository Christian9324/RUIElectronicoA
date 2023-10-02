package com.example.electrorui.usecase

//import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.RegistroNacionalidad
import javax.inject.Inject

class SetRegistroFamiliaUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(registros: List<RegistroFamilias>) {

        if (!registros.isNullOrEmpty()){
            repository.insertRegistroFamilia(registros)
        } else{

        }
    }
}