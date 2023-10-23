package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.RegistroFamilias

@Entity(tableName = "datos_registro_familias_table")
data class RegistroFamiliasEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdRegistroFamilia") val idRegistroFam: Int = 0,
    @ColumnInfo(name = "Nacionalidad") val nacionalidad : String,
    @ColumnInfo(name = "iso3") val iso3 : String,
    @ColumnInfo(name = "Nombre") val nombre : String,
    @ColumnInfo(name = "Apellidos") val apellidos : String,
    @ColumnInfo(name = "No de Identidad") val noIdentidad : String,
    @ColumnInfo(name = "Parentesco") val parentesco : String,
    @ColumnInfo(name = "Fecha Nacimiento") val fechaNacimiento : String,
    @ColumnInfo(name = "Adulto") val adulto : Boolean,
    @ColumnInfo(name = "Sexo") val sexo : Boolean,
    @ColumnInfo(name = "Embarazo") val embarazo : Boolean,
    @ColumnInfo(name = "Numero de Familia") val numFamilia : Int,
)

fun RegistroFamilias.toDB() = RegistroFamiliasEntity(
    nacionalidad = nacionalidad,
    iso3 = iso3,
    nombre = nombre,
    apellidos = apellidos,
    noIdentidad = noIdentidad,
    parentesco = parentesco,
    fechaNacimiento = fechaNacimiento,
    adulto = adulto,
    sexo = sexo,
    embarazo = embarazo,
    numFamilia = numFamilia,
)

fun RegistroFamilias.toUpdateDB() = RegistroFamiliasEntity(
    idRegistroFam = idF,
    nacionalidad = nacionalidad,
    iso3 = iso3,
    nombre = nombre,
    apellidos = apellidos,
    noIdentidad = noIdentidad,
    parentesco = parentesco,
    fechaNacimiento = fechaNacimiento,
    adulto = adulto,
    sexo = sexo,
    embarazo = embarazo,
    numFamilia = numFamilia,
)
