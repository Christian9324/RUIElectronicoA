package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.Rescate

@Entity(tableName = "tipo_rescate_table")
data class RescateEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdRescate") val idRescate: Int = 0,
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
)

fun Rescate.toDB() = RescateEntity(
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
)
