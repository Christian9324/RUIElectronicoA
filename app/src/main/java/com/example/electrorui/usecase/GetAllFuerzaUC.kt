package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toFuerzaDB
import com.example.electrorui.usecase.model.Fuerza
import javax.inject.Inject

class GetAllFuerzaUC @Inject constructor(
    private val repository : RepositoryApp
) {
        suspend operator fun invoke(): List<Fuerza> {
            val listFuerza = repository.getAllFuerzaFromApi()

//            Log.e("error de datos", listFuerza.toString())

            return if (!listFuerza.isNullOrEmpty()){
                repository.deleteAllFuerza()
                repository.insertFuerzas(listFuerza)
                listFuerza
            } else{
                val DBFuerza = repository.getAllFuerzaFromDB()
                if(!DBFuerza.isNullOrEmpty()){
                    DBFuerza
                } else {
                    val nullFuerza = Fuerza("", 0, "", "", "", "", 0.0, 0.0, 0, 0, 0, 0,0,0,0)
                    listOf(nullFuerza)
                }
            }
        }
}