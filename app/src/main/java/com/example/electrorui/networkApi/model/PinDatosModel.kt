package com.example.electrorui.networkApi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PinDatosModel(
    val oficinaR : String,
    val fecha : String,
    val hora : String,
    val tipoRescate : String,
    val rescates : List<RegistroNacionalidadModel>
) : Parcelable
