package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Fuerza
import com.example.electrorui.usecase.model.Municipios
import com.example.electrorui.usecase.model.PuntosInter
import javax.inject.Inject


class GetAllPuntosIApi @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke() : List<PuntosInter> {
        val listPuntosI = repository.getAllPuntosInterFromApi()

        return if (!listPuntosI.isNullOrEmpty()){
            repository.deleteAllPuntosI()
            repository.insertPuntosI(listPuntosI)
            listPuntosI
        } else{
            val DBPuntosI = repository.getAllPuntosInterFromDB()
            if(!DBPuntosI.isNullOrEmpty()){
                DBPuntosI
            } else {
                val nullPuntosI = PuntosInter("", "", "",)
                listOf(nullPuntosI)
            }
        }
    }
}