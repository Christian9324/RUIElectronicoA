package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Fuerza
import com.example.electrorui.usecase.model.Municipios
import com.example.electrorui.usecase.model.PuntosInter
import javax.inject.Inject


class GetAllPuntosIDB @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke() : List<PuntosInter> {
        val listPuntosI = repository.getAllPuntosInterFromDB()

        return if(!listPuntosI.isNullOrEmpty()){
            listPuntosI
        } else {
            val nullPuntosI = PuntosInter("", "", "",)
            listOf(nullPuntosI)
        }
    }
}