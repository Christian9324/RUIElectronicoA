package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.Fuerza
import com.example.electrorui.usecase.model.Mensaje
import com.example.electrorui.usecase.model.Municipios
import com.example.electrorui.usecase.model.PuntosInter
import javax.inject.Inject


class GetAllMensajesDB @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke() : List<Mensaje> {
        val mensajesDB = repository.getAllMensajesFromDB()

        return if(!mensajesDB.isNullOrEmpty()){
            mensajesDB
        } else {
            val nullMensaje = Mensaje(0, "")
            listOf(nullMensaje)
        }
    }
}