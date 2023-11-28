package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.Mensaje

@Entity(tableName = "mensaje_table")
data class MensajeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdMensaje") val id : Int = 0,
    @ColumnInfo(name = "mensaje") val mensaje : String
)

fun Mensaje.toDB() = MensajeEntity(
    mensaje = mensaje)