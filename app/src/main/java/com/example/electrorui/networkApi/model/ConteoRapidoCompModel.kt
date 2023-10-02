package com.example.electrorui.networkApi.model

import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.google.gson.annotations.SerializedName

data class ConteoRapidoCompModel(
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

    @SerializedName("AS_hombres") val AS_hombres : Int,
    @SerializedName("AS_mujeresNoEmb") val AS_mujeresNoEmb : Int,
    @SerializedName("AS_mujeresEmb") val AS_mujeresEmb : Int,
    @SerializedName("nucleosFamiliares") val nucleosFamiliares : Int,
    @SerializedName("AA_hombres") val AA_NNAs_hombres : Int,
    @SerializedName("AA_mujeresNoEmb") val AA_NNAs_mujeresNoEmb : Int,
    @SerializedName("AA_mujeresEmb") val AA_NNAs_mujeresEmb : Int,
    @SerializedName("NNA_A_hombres") val NNAsA_hombres : Int,
    @SerializedName("NNA_A_mujeresNoEmb") val NNAsA_mujeresNoEmb : Int,
    @SerializedName("NNA_A_mujeresEmb") val NNAsA_mujeresEmb : Int,
    @SerializedName("NNA_S_hombres") val NNAsS_hombres : Int,
    @SerializedName("NNA_S_mujeresNoEmb") val NNAsS_mujeresNoEmb : Int,
    @SerializedName("NNA_S_mujeresEmb") val NNAsS_mujeresEmb : Int,
)

fun ConteoRapidoComp.toAPI() = ConteoRapidoCompModel(
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
    AS_hombres, AS_mujeresNoEmb, AS_mujeresEmb,
    nucleosFamiliares,
    AA_NNAs_hombres, AA_NNAs_mujeresNoEmb, AA_NNAs_mujeresEmb,
    NNAsA_hombres, NNAsA_mujeresNoEmb, NNAsA_mujeresEmb,
    NNAsS_hombres, NNAsS_mujeresNoEmb, NNAsS_mujeresEmb)
