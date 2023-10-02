package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.PuntosInter

@Entity(tableName = "puntos_internacion_table")
data class PuntoIEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdPuntoI") val idUsuario : Int = 0,
    @ColumnInfo(name = "nombrePunto") val nombrePunto : String,
    @ColumnInfo(name = "estadoPunto") val estadoPunto : String,
    @ColumnInfo(name = "tipoPunto") val tipoPunto : String,

)

fun PuntosInter.toDB() = PuntoIEntity(
    nombrePunto = nombrePunto,
    estadoPunto = estadoPunto,
    tipoPunto = tipoPunto,
)