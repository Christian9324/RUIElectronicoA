package com.example.electrorui.usecase.model


data class PinFamilias(
        val nacionalidad: String,
        val totales : Int,
        val sexo : Boolean,
        val adulto : Boolean,
        val familia : Int,
    )

//fun PuntosInterModel.toUC() = PuntosInter(nombrePunto, estadoPunto, tipoPunto)
//
//fun PuntoIEntity.toUC() = PuntosInter(
//    nombrePunto, estadoPunto, tipoPunto)