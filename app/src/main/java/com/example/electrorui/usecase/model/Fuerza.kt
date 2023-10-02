package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.FuerzaEntity
import com.example.electrorui.networkApi.model.FuerzaModel
import com.google.gson.annotations.SerializedName

data class Fuerza(
    val OficinaR : String,
    val numPunto : Int,
    val NomPuntoRevision : String,
    val tipoP : String,
    val ubicacion : String,
    val coordenadasT : String,
    val latitud : Double,
    val longitud : Double,
    val personalINM : Int,
    val personalSedena : Int,
    val personalMarina : Int,
    val personalGuardiaN : Int,
    val personalOtros : Int,
    val vehiculos : Int,
    val seccion : Int,
)


fun FuerzaModel.toUC() = Fuerza(
    OficinaR,
    numPunto,
    NomPuntoRevision,
    tipoP,
    ubicacion,
    coordenadasT,
    latitud,
    longitud,
    personalINM,
    personalSedena,
    personalMarina,
    personalGuardiaN,
    personalOtros,
    vehiculos,
    seccion)

fun FuerzaEntity.toUC() = Fuerza(
    OficinaR,
    numPunto,
    NomPuntoRevision,
    tipoP,
    ubicacion,
    coordenadasT,
    latitud,
    longitud,
    personalINM,
    personalSedena,
    personalMarina,
    personalGuardiaN,
    personalOtros,
    vehiculos,
    seccion)