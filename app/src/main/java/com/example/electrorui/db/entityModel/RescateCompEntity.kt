package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.RescateComp

@Entity(tableName = "rescate_completo_table")
data class RescateCompEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdRescateC") val idRescate: Int = 0,
    @ColumnInfo(name = "Oficina de Representación") val oficinaRepre: String,
    @ColumnInfo(name = "Fecha") val fecha: String,
    @ColumnInfo(name = "Hora") val hora: String,

    @ColumnInfo(name = "Aeropuerto") val aeropuerto : Boolean,

    @ColumnInfo(name = "Carretero") val carretero : Boolean,
    @ColumnInfo(name = "Tipo de Vehículo") val tipoVehic : String,
    @ColumnInfo(name = "Linea de Autobus") val lineaAutobus : String,
    @ColumnInfo(name = "No Ecónomico") val numeroEcono : String,
    @ColumnInfo(name = "Placas") val placas : String,
//    @ColumnInfo(name = "Descripción Vehículo") val descripcionVehi : String,
    @ColumnInfo(name = "Vehículo Asegurado") val vehiculoAseg : Boolean,

    @ColumnInfo(name = "Casa de Seguridad") val casaSeguridad : Boolean,

    @ColumnInfo(name = "Central de Autobus") val centralAutobus : Boolean,

    @ColumnInfo(name = "Ferrocarril") val ferrocarril : Boolean,
    @ColumnInfo(name = "Empresa") val empresa: String,

    @ColumnInfo(name = "Hotel") val hotel : Boolean,
    @ColumnInfo(name = "Nombre Hotel") val nombreHotel : String,

    @ColumnInfo(name = "Puestos a disposición") val puestosADispo : Boolean,
    @ColumnInfo(name = "Juez Calificador") val juezCalif : Boolean,
    @ColumnInfo(name = "Reclusorio") val reclusorio : Boolean,
    @ColumnInfo(name = "Policía Federal") val policiaFede : Boolean,
    @ColumnInfo(name = "DIF") val dif : Boolean,
    @ColumnInfo(name = "Policía Estatal") val policiaEsta : Boolean,
    @ColumnInfo(name = "Policía Municipal") val policiaMuni : Boolean,
    @ColumnInfo(name = "Guardia Nacional") val guardiaNaci : Boolean,
    @ColumnInfo(name = "Fiscalia") val fiscalia : Boolean,
    @ColumnInfo(name = "Otras Autoriadades") val otrasAuto : Boolean,

    @ColumnInfo(name = "Voluntarios") val voluntarios : Boolean,

    @ColumnInfo(name = "Otro") val otro : Boolean,

    @ColumnInfo(name = "Presuntos Delincuentes") val presuntosDelincuentes : Boolean,
    @ColumnInfo(name = "No de Presuntos Delincuentes") val numPresuntosDelincuentes : Int,
    @ColumnInfo(name = "Municipio") val municipio : String,

    @ColumnInfo(name = "Punto Estratégico") val puntoEstra : String,

    @ColumnInfo(name = "Nacionalidad") val nacionalidad : String,
    @ColumnInfo(name = "iso3") val iso3 : String,
    @ColumnInfo(name = "Nombre") val nombre : String,
    @ColumnInfo(name = "Apellidos") val apellidos : String,
    @ColumnInfo(name = "No de Identidad") val noIdentidad : String,
    @ColumnInfo(name = "Parentesco") val parentesco : String,
    @ColumnInfo(name = "Fecha Nacimiento") val fechaNacimiento : String,
    @ColumnInfo(name = "Sexo") val sexo : Boolean,
    @ColumnInfo(name = "Numero de Familia") val numFamilia : Int,

    @ColumnInfo(name = "Edad") val edad : Int,

    )

fun RescateComp.toDB() = RescateCompEntity(
    oficinaRepre = oficinaRepre,
    fecha = fecha,
    hora = hora,
    aeropuerto = aeropuerto,
    carretero = carretero,
    tipoVehic = tipoVehic,
    lineaAutobus = lineaAutobus,
    numeroEcono = numeroEcono,
    placas = placas,
    vehiculoAseg = vehiculoAseg,
    casaSeguridad = casaSeguridad,
    centralAutobus = centralAutobus,
    ferrocarril = ferrocarril,
    empresa = empresa,
    hotel = hotel,
    nombreHotel = nombreHotel,
    puestosADispo = puestosADispo,
    juezCalif = juezCalif,
    reclusorio = reclusorio,
    policiaFede = policiaFede,
    dif = dif,
    policiaEsta = policiaEsta,
    policiaMuni = policiaMuni,
    guardiaNaci = guardiaNaci,
    fiscalia = fiscalia,
    otrasAuto = otrasAuto,
    voluntarios = voluntarios,
    otro = otro,
    presuntosDelincuentes = presuntosDelincuentes,
    numPresuntosDelincuentes = numPresuntosDelincuentes,
    municipio = municipio,
    puntoEstra = puntoEstra,
    nacionalidad = nacionalidad,
    iso3 = iso3,
    nombre = nombre,
    apellidos = apellidos,
    noIdentidad = noIdentidad,
    parentesco = parentesco,
    fechaNacimiento = fechaNacimiento,
    sexo = sexo,
    numFamilia = numFamilia,
    edad = edad,

)
