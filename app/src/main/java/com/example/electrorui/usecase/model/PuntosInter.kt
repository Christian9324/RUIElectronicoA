package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.PuntoIEntity
import com.example.electrorui.networkApi.model.PuntosInterModel


data class PuntosInter(
        val nombrePunto: String,
        val estadoPunto : String,
        val tipoPunto : String,
    )

fun PuntosInterModel.toUC() = PuntosInter(nombrePunto, estadoPunto, tipoPunto)

fun PuntoIEntity.toUC() = PuntosInter(nombrePunto, estadoPunto, tipoPunto)