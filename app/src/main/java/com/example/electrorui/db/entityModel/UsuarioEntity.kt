package com.example.electrorui.db.entityModel

import android.provider.ContactsContract.CommonDataKinds.Nickname
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.User

@Entity(tableName = "usuario_table")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdUsuario") val idUsuario : Int = 0,
    @ColumnInfo(name = "Nickname") val nickname : String,
    @ColumnInfo(name = "Nombre") val nombre : String,
    @ColumnInfo(name = "Apellidos") val apellido : String,
    @ColumnInfo(name = "Password") val password : String,
    @ColumnInfo(name = "EstadoOR") val estado : String,
    @ColumnInfo(name = "TipoU") val tipo : String,
)

fun User.toDB() = UsuarioEntity(nickname = nickname, nombre = nombre, apellido = apellido, password = password, estado = estado, tipo = tipo )