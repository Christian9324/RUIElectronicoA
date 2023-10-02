package com.example.electrorui.usecase.model

import android.os.Parcelable
import com.example.electrorui.db.entityModel.DatosRegistroEntity
import com.example.electrorui.networkApi.model.RegistroNacionalidadModel
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegistroNacionalidad(
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
) : Parcelable

fun RegistroNacionalidadModel.toUC() = RegistroNacionalidad(
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