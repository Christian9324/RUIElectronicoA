package com.example.electrorui.networkApi.model

import com.example.electrorui.db.entityModel.RescateCompEntity
import com.example.electrorui.usecase.model.RescateComp
import com.google.gson.annotations.SerializedName

data class RescateCompModel(
    @SerializedName("oficinaRepre") val oficinaRepre: String,
    @SerializedName("fecha") val fecha: String,
    @SerializedName("hora") val hora: String,

    @SerializedName("aeropuerto") val aeropuerto : Boolean,

    @SerializedName("carretero") val carretero : Boolean,
    @SerializedName("tipoVehic") val tipoVehic : String,
    @SerializedName("lineaAutobus") val lineaAutobus : String,
    @SerializedName("numeroEcono") val numeroEcono : String,
    @SerializedName("placas") val placas : String,
    @SerializedName("vehiculoAseg") val vehiculoAseg : Boolean,

    @SerializedName("casaSeguridad") val casaSeguridad : Boolean,

    @SerializedName("centralAutobus") val centralAutobus : Boolean,

    @SerializedName("ferrocarril") val ferrocarril : Boolean,
    @SerializedName("empresa") val empresa: String,

    @SerializedName("hotel") val hotel : Boolean,
    @SerializedName("nombreHotel") val nombreHotel : String,

    @SerializedName("puestosADispo") val puestosADispo : Boolean,
    @SerializedName("juezCalif") val juezCalif : Boolean,
    @SerializedName("reclusorio") val reclusorio : Boolean,
    @SerializedName("policiaFede") val policiaFede : Boolean,
    @SerializedName("dif") val dif : Boolean,
    @SerializedName("policiaEsta") val policiaEsta : Boolean,
    @SerializedName("policiaMuni") val policiaMuni : Boolean,
    @SerializedName("guardiaNaci") val guardiaNaci : Boolean,
    @SerializedName("fiscalia") val fiscalia : Boolean,
    @SerializedName("otrasAuto") val otrasAuto : Boolean,

    @SerializedName("voluntarios") val voluntarios : Boolean,

    @SerializedName("otro") val otro : Boolean,

    @SerializedName("presuntosDelincuentes") val presuntosDelincuentes : Boolean,
    @SerializedName("numPresuntosDelincuentes") val numPresuntosDelincuentes : Int,
    @SerializedName("municipio") val municipio : String,

    @SerializedName("puntoEstra") val puntoEstra : String,

    @SerializedName("nacionalidad") val nacionalidad : String,
    @SerializedName("iso3") val iso3 : String,
    @SerializedName("nombre") val nombre : String,
    @SerializedName("apellidos") val apellidos : String,
    @SerializedName("noIdentidad") val noIdentidad : String,
    @SerializedName("parentesco") val parentesco : String,
    @SerializedName("fechaNacimiento") val fechaNacimiento : String,
    @SerializedName("sexo") val sexo : Boolean,
    @SerializedName("numFamilia") val numFamilia : Int,
    @SerializedName("edad") val edad : Int,
    )

fun RescateComp.toAPI() = RescateCompModel(
    oficinaRepre, fecha, hora,
    aeropuerto,
    carretero, tipoVehic, lineaAutobus, numeroEcono, placas, vehiculoAseg,
    casaSeguridad,
    centralAutobus,
    ferrocarril, empresa,
    hotel, nombreHotel,
    puestosADispo, juezCalif, reclusorio, policiaFede, dif, policiaEsta, policiaMuni, guardiaNaci, fiscalia, otrasAuto,
    voluntarios,
    otro,
    presuntosDelincuentes, numPresuntosDelincuentes,
    municipio, puntoEstra,
    nacionalidad, iso3,
    nombre, apellidos, noIdentidad, parentesco, fechaNacimiento, sexo, numFamilia, edad,
)

//
//fun RescateComp.toDB() = RescateCompEntity(
//    oficinaRepre = oficinaRepre,
//    fecha = fecha,
//    hora = hora,
//    aeropuerto = aeropuerto,
//    carretero = carretero,
//    tipoVehic = tipoVehic,
//    lineaAutobus = lineaAutobus,
//    numeroEcono = numeroEcono,
//    placas = placas,
//    vehiculoAseg = vehiculoAseg,
//    casaSeguridad = casaSeguridad,
//    centralAutobus = centralAutobus,
//    ferrocarril = ferrocarril,
//    empresa = empresa,
//    hotel = hotel,
//    nombreHotel = nombreHotel,
//    puestosADispo = puestosADispo,
//    juezCalif = juezCalif,
//    reclusorio = reclusorio,
//    policiaFede = policiaFede,
//    dif = dif,
//    policiaEsta = policiaEsta,
//    policiaMuni = policiaMuni,
//    guardiaNaci = guardiaNaci,
//    fiscalia = fiscalia,
//    otrasAuto = otrasAuto,
//    voluntarios = voluntarios,
//    otro = otro,
//    presuntosDelincuentes = presuntosDelincuentes,
//    numPresuntosDelincuentes = numPresuntosDelincuentes,
//    municipio = municipio,
//    puntoEstra = puntoEstra,
//    nacionalidad = nacionalidad,
//    iso3 = iso3,
//    nombre = nombre,
//    apellidos = apellidos,
//    noIdentidad = noIdentidad,
//    parentesco = parentesco,
//    fechaNacimiento = fechaNacimiento,
//    sexo = sexo,
//    numFamilia = numFamilia,
//    edad = edad,
//
//)
