package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.UsuarioEntity
import com.example.electrorui.networkApi.model.LoginModel
import com.google.gson.annotations.SerializedName

data class User(
    val nickname: String,
    val nombre: String,
    val apellido: String,
    val password: String,
    val estado: String,
    val tipo: String,
)

fun LoginModel.toUser() = User(nickname, nombre, apellido, password, estado, tipo)

fun UsuarioEntity.toUC() = User(nickname, nombre, apellido, password, estado, tipo)