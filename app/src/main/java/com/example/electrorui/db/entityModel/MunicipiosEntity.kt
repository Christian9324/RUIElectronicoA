package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.Municipios

@Entity(tableName = "municipios_table")
data class MunicipiosEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdMunicipios") val id : Int = 0,
    @ColumnInfo(name ="estado") val estado: String,
    @ColumnInfo(name ="estadoAbr") val estadoAbr : String,
    @ColumnInfo(name ="nomMunicipio") val nomMunicipio : String,
)

fun Municipios.toDB() = MunicipiosEntity(estado = estado, estadoAbr = estadoAbr, nomMunicipio = nomMunicipio)
