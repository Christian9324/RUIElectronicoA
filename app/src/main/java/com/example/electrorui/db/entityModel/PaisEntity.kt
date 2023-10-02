package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.Pais

@Entity(tableName = "pais_table")
data class PaisEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdPais") val idPais : Int = 0,
    @ColumnInfo(name = "Pais") val pais : String,
    @ColumnInfo(name = "iso3") val iso3 : String,
)

fun Pais.toPaisDB() = PaisEntity(pais = pais, iso3 = iso3)
