package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.RegistroNombresEntity
import java.text.SimpleDateFormat


data class RegistroNombres(
    val idNombres: Int,
    val nacionalidad : String,
    val iso3 : String,
    val nombre : String,
    val apellidos : String,
    val noIdentidad : String,
    val fechaNacimiento : String,
    val adulto : Boolean,
    val sexo : Boolean,
    val embarazo : Boolean,
) {
    fun getEdad() : Int {

        val fechaNacimiento = this.fechaNacimiento
        val fechaNacimientoDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento)
        val fechaActual = java.util.Date(System.currentTimeMillis())
        val diferencia = fechaActual.time - fechaNacimientoDate?.time!!
        val edad : Float = diferencia.toFloat() / (31536000000)

        return edad.toInt()
    }

    fun getAdultoT() : Boolean{
        return this.getEdad() > 18
    }
}

fun RegistroNombresEntity.toUC() = RegistroNombres(
    idRegistroNom,
    nacionalidad,
    iso3,
    nombre,
    apellidos,
    noIdentidad,
    fechaNacimiento,
    adulto,
    sexo,
    embarazo,
)
