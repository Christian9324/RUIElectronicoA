package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.IsoEntityA

data class Iso(
    val iso3 : String,
    val conteo : Int
)

fun IsoEntityA.toUC() = Iso(iso3, conteo)
