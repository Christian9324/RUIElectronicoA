package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.DatosRegistroEntity
import com.example.electrorui.networkApi.model.RegistroNacionalidadModel


data class RegistroNacionalidad(
    val idRegistro : Int,
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
)

fun RegistroNacionalidadModel.toUC() = RegistroNacionalidad(
    1,
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
    NNAsS_mujeresEmb
)

fun DatosRegistroEntity.toUC() = RegistroNacionalidad(
    idRegistro,
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
    NNAsS_mujeresEmb
)

fun DatosRegistroEntity.toUpdate() = RegistroNacionalidad(
    idRegistro,
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
    NNAsS_mujeresEmb
)