package com.example.electrorui.networkApi.model

import com.google.gson.annotations.SerializedName


data class PaisModel(
    @SerializedName("nombre_pais") val pais: String,
    @SerializedName("iso3") val iso3 : String,
)