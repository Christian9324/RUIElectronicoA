package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.PaisEntity
import com.example.electrorui.networkApi.model.LoginModel
import com.example.electrorui.networkApi.model.PaisModel
import com.google.gson.annotations.SerializedName

data class Pais(
    val pais : String,
    val iso3 : String
)

fun PaisEntity.toPaisUC() = Pais(pais, iso3)
fun PaisModel.toPaisUC() = Pais(pais, iso3)

