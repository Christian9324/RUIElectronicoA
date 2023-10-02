package com.example.electrorui.networkApi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegistroNacionalidadModel(
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
