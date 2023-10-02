package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.PuntoIEntity
import com.example.electrorui.networkApi.model.PuntosInterModel


data class PinNacionalidad(
        val nacionalidad: String,
        val totales : Int,
        val sexo : Boolean,
        val adulto : Boolean,
    )

//fun PuntosInterModel.toUC() = PuntosInter(nombrePunto, estadoPunto, tipoPunto)
//
//fun PuntoIEntity.toUC() = PuntosInter(
//    nombrePunto, estadoPunto, tipoPunto)