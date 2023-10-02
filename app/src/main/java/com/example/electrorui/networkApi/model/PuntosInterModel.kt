package com.example.electrorui.networkApi.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PuntosInterModel(
    @SerializedName("nombrePunto") val nombrePunto: String,
    @SerializedName("estadoPunto") val estadoPunto : String,
    @SerializedName("tipoPunto") val tipoPunto : String,
) : Parcelable
