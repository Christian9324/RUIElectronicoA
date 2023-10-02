package com.example.electrorui.networkApi.model

import com.example.electrorui.usecase.model.User
import com.google.gson.annotations.SerializedName


data class LoginModel(
    @SerializedName("nickname") val nickname: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("apellido") val apellido: String,
    @SerializedName("password") val password: String,
    val estado: String,
    val tipo: String
)

fun User.toApi() = LoginModel(
    nickname = nickname,
    nombre = nombre,
    apellido = apellido ,
    password = password,
    estado = estado,
    tipo = tipo,
    )