package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.RegistroFamiliasEntity
import java.text.SimpleDateFormat
import java.util.Date

data class RegistroFamilias(
    val idF : Int,
    val nacionalidad : String,
    val iso3 : String,
    val nombre : String,
    val apellidos : String,
    val noIdentidad : String,
    val parentesco : String,
    val fechaNacimiento : String,
    val adulto : Boolean,
    val sexo : Boolean,
    val embarazo : Boolean,
    val numFamilia : Int,
) {
    fun getEdad() : Int {

        val fechaNacimiento = this.fechaNacimiento
        val fechaNacimientoDate = SimpleDateFormat("dd/MM/yyyy").parse(fechaNacimiento)
        val fechaActual = Date(System.currentTimeMillis())
        val diferencia = fechaActual.time - fechaNacimientoDate?.time!!
        val edad : Float = diferencia.toFloat() / (31536000000)

        return edad.toInt()
    }

    fun getAdultoT() : Boolean{
        return this.getEdad() > 18
    }
}

fun RegistroFamiliasEntity.toUC() = RegistroFamilias(
    idRegistroFam,
    nacionalidad,
    iso3,
    nombre,
    apellidos,
    noIdentidad,
    parentesco,
    fechaNacimiento,
    adulto,
    sexo,
    embarazo,
    numFamilia,
)
