package com.example.electrorui.networkApi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MunicipiosModel(
    @SerializedName("estado") val estado: String,
    @SerializedName("estadoAbr") val estadoAbr : String,
    @SerializedName("nomMunicipio") val nomMunicipio : String,
) : Parcelable
