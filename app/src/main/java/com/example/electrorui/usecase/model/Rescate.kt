package com.example.electrorui.usecase.model

import androidx.room.ColumnInfo
import com.example.electrorui.db.entityModel.RescateEntity


data class Rescate(

    var oficinaRepre: String,
    var fecha: String,
    var hora: String,

    var nombreAgente: String,

    var aeropuerto : Boolean,

    var carretero : Boolean,
    var tipoVehic : String,
    var lineaAutobus : String,
    var numeroEcono : String,
    var placas : String,
//    var descripcion : String,
    var vehiculoAseg : Boolean,

    var casaSeguridad : Boolean,

    var centralAutobus : Boolean,

    var ferrocarril : Boolean,
    var empresa: String,

    var hotel : Boolean,
    var nombreHotel : String,

    var puestosADispo : Boolean,
    var juezCalif : Boolean,
    var reclusorio : Boolean,
    var policiaFede : Boolean,
    var dif : Boolean,
    var policiaEsta : Boolean,
    var policiaMuni : Boolean,
    var guardiaNaci : Boolean,
    var fiscalia : Boolean,
    var otrasAuto : Boolean,

    var voluntarios : Boolean,

    var otro : Boolean,

    var presuntosDelincuentes : Boolean,
    var numPresuntosDelincuentes : Int,
    var municipio : String,

    var puntoEstra : String,
){
    constructor(oficinaRepre: String, fecha: String, hora: String, nombreAgente : String, tipoRescate: TipoRescate) : this(
        oficinaRepre,
        fecha,
        hora,
        nombreAgente,
        tipoRescate.aeropuerto,
        tipoRescate.carretero,
        tipoRescate.tipoVehic,
        tipoRescate.lineaAutobus,
        tipoRescate.numeroEcono,
        tipoRescate.placas,
        tipoRescate.vehiculoAseg,
        tipoRescate.casaSeguridad,
        tipoRescate.centralAutobus,
        tipoRescate.ferrocarril,
        tipoRescate.empresa,
        tipoRescate.hotel,
        tipoRescate.nombreHotel,
        tipoRescate.puestosADispo,
        tipoRescate.juezCalif,
        tipoRescate.reclusorio,
        tipoRescate.policiaFede,
        tipoRescate.dif,
        tipoRescate.policiaEsta,
        tipoRescate.policiaMuni,
        tipoRescate.guardiaNaci,
        tipoRescate.fiscalia,
        tipoRescate.otrasAuto,
        tipoRescate.voluntarios,
        tipoRescate.otro,
        tipoRescate.presuntosDelincuentes,
        tipoRescate.numPresuntosDelincuentes,
        tipoRescate.municipio,
        tipoRescate.puntoEstra
    )
}

fun RescateEntity.toUC() = Rescate(
    oficinaRepre,
    fecha,
    hora,
    nombreAgente,
    aeropuerto,
    carretero,
    tipoVehic,
    lineaAutobus,
    numeroEcono,
    placas,
    vehiculoAseg,
    casaSeguridad,
    centralAutobus,
    ferrocarril,
    empresa,
    hotel,
    nombreHotel,
    puestosADispo,
    juezCalif,
    reclusorio,
    policiaFede,
    dif,
    policiaEsta,
    policiaMuni,
    guardiaNaci,
    fiscalia,
    otrasAuto,
    voluntarios,
    otro,
    presuntosDelincuentes,
    numPresuntosDelincuentes,
    municipio,
    puntoEstra)
