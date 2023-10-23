package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.RegistroNombres

@Entity(tableName = "datos_registro_nombres_table")
data class RegistroNombresEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdRegistroNom") val idRegistroNom: Int = 0,
    @ColumnInfo(name = "Nacionalidad") val nacionalidad : String,
    @ColumnInfo(name = "iso3") val iso3 : String,
    @ColumnInfo(name = "Nombre") val nombre : String,
    @ColumnInfo(name = "Apellidos") val apellidos : String,
    @ColumnInfo(name = "No de Identidad") val noIdentidad : String,
    @ColumnInfo(name = "Fecha Nacimiento") val fechaNacimiento : String,
    @ColumnInfo(name = "Adulto") val adulto : Boolean,
    @ColumnInfo(name = "Sexo") val sexo : Boolean,
    @ColumnInfo(name = "Embarazo") val embarazo : Boolean,
)

fun RegistroNombres.toDB() = RegistroNombresEntity(
    nacionalidad = nacionalidad,
    iso3 = iso3,
    nombre = nombre,
    apellidos = apellidos,
    noIdentidad = noIdentidad,
    fechaNacimiento = fechaNacimiento,
    adulto = adulto,
    sexo = sexo,
    embarazo = embarazo,
)

fun RegistroNombres.toUpdateDB() = RegistroNombresEntity(
    idRegistroNom = idNombres,
    nacionalidad = nacionalidad,
    iso3 = iso3,
    nombre = nombre,
    apellidos = apellidos,
    noIdentidad = noIdentidad,
    fechaNacimiento = fechaNacimiento,
    adulto = adulto,
    sexo = sexo,
    embarazo = embarazo,
)
