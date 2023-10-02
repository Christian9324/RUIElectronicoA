package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.MensajeEntity


data class Mensaje(
    val id : Int = 0,
    val mensaje : String
)

fun MensajeEntity.toUC() = Mensaje(id, mensaje)