package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.ConteoRapidoCompEntity

data class ConteoRapidoComp(
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

    val nacionalidad : String,
    val iso3 : String,
    val AS_hombres : Int,
    val AS_mujeresNoEmb : Int,
    val AS_mujeresEmb : Int,
    val nucleosFamiliares : Int,
    val AA_NNAs_hombres : Int,
    val AA_NNAs_mujeresNoEmb : Int,
    val AA_NNAs_mujeresEmb : Int,
    val NNAsA_hombres : Int,
    val NNAsA_mujeresNoEmb : Int,
    val NNAsA_mujeresEmb : Int,
    val NNAsS_hombres : Int,
    val NNAsS_mujeresNoEmb : Int,
    val NNAsS_mujeresEmb : Int,
) {
    constructor(rescate: Rescate, registro: RegistroNacionalidad) : this(
        rescate.oficinaRepre,
        rescate.fecha,
        rescate.hora,

        rescate.nombreAgente,

        rescate.aeropuerto,

        rescate.carretero,
        rescate.tipoVehic,
        rescate.lineaAutobus,
        rescate.numeroEcono,
        rescate.placas,
        rescate.vehiculoAseg,

        rescate.casaSeguridad,

        rescate.centralAutobus,

        rescate.ferrocarril,
        rescate.empresa,

        rescate.hotel,
        rescate.nombreHotel,

        rescate.puestosADispo,
        rescate.juezCalif,
        rescate.reclusorio,
        rescate.policiaFede,
        rescate.dif,
        rescate.policiaEsta,
        rescate.policiaMuni,
        rescate.guardiaNaci,
        rescate.fiscalia,
        rescate.otrasAuto,

        rescate.voluntarios,
        rescate.otro,

        rescate.presuntosDelincuentes,
        rescate.numPresuntosDelincuentes,

        rescate.municipio,
        rescate.puntoEstra,

        registro.nacionalidad,
        registro.iso3,

        registro.AS_hombres,
        registro.AS_mujeresNoEmb,
        registro.AS_mujeresEmb,

        registro.nucleosFamiliares,
        registro.AA_NNAs_hombres,
        registro.AA_NNAs_mujeresNoEmb,
        registro.AA_NNAs_mujeresEmb,
        registro.NNAsA_hombres,
        registro.NNAsA_mujeresNoEmb,
        registro.NNAsA_mujeresEmb,

        registro.NNAsS_hombres,
        registro.NNAsS_mujeresNoEmb,
        registro.NNAsS_mujeresEmb
    )
}

fun ConteoRapidoCompEntity.toUC() = ConteoRapidoComp(
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
    puntoEstra,

    nacionalidad,
    iso3,
    AS_hombres,
    AS_mujeresNoEmb,
    AS_mujeresEmb,
    nucleosFamiliares,
    AA_NNAs_hombres,
    AA_NNAs_mujeresNoEmb,
    AA_NNAs_mujeresEmb,
    NNAsA_hombres,
    NNAsA_mujeresNoEmb,
    NNAsA_mujeresEmb,
    NNAsS_hombres,
    NNAsS_mujeresNoEmb,
    NNAsS_mujeresEmb)