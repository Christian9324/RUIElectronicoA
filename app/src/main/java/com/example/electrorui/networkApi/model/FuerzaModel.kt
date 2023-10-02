package com.example.electrorui.networkApi.model

import com.google.gson.annotations.SerializedName

data class FuerzaModel(
    @SerializedName("oficinaR") val OficinaR : String,
    @SerializedName("numPunto") val numPunto : Int,
    @SerializedName("nomPuntoRevision") val NomPuntoRevision : String,
    @SerializedName("tipoP") val tipoP : String,
    @SerializedName("ubicacion") val ubicacion : String,
    @SerializedName("coordenadasTexto") val coordenadasT : String,
    @SerializedName("latitud") val latitud : Double,
    @SerializedName("longitud") val longitud : Double,
    @SerializedName("personalINM") val personalINM : Int,
    @SerializedName("personalSEDENA") val personalSedena : Int,
    @SerializedName("personalMARINA") val personalMarina : Int,
    @SerializedName("personalGuardiaN") val personalGuardiaN : Int,
    @SerializedName("personalOTROS") val personalOtros : Int,
    @SerializedName("vehiculos") val vehiculos : Int,
    @SerializedName("seccion") val seccion : Int,
)
