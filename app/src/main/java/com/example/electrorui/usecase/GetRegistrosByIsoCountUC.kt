package com.example.electrorui.usecase

//import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Iso
import javax.inject.Inject

class GetRegistrosByIsoCountUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke() : List<Iso>{

        return repository.getAllByIsoFromDB()
//        val registros = repository.getAllByIsoFromDB()
//        return if (!registros.isNullOrEmpty()){
//            registros
//        } else{
//            val registroV = Iso("", 0)
//            listOf(registroV)
//        }
    }
}