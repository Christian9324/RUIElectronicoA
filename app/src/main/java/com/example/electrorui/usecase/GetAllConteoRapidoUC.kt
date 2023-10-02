package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toFuerzaDB
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.Fuerza
import javax.inject.Inject

class GetAllConteoRapidoUC @Inject constructor(
    private val repository : RepositoryApp
) {
        suspend operator fun invoke(): List<ConteoRapidoComp> {
            val listConteoRapidoComp = repository.getAllDataConteoRapidoFromDB()

//            Log.e("error de datos", listFuerza.toString())

//            return listConteoRapidoComp

            return if (!listConteoRapidoComp.isNullOrEmpty()){
                listConteoRapidoComp
            } else{
                val nullConteo = ConteoRapidoComp(
                    "",
                    "",
                    "",
                    false,
                    false,
                    "",
                    "",
                    "",
                    "",
                    false,
                    false,
                    false,
                    false,
                    "",
                    false,"",false,false,false,false,false,false,false,false,false,false,
                    false,
                    false,
                    false,0,
                    "", "",
                    "", "",
                    0,0,0,
                    0,
                    0,0,0,
                    0,0,0,
                    0,0,0,
                    )
                listOf(nullConteo)
            }
        }
}