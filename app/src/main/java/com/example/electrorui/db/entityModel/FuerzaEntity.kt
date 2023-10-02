package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.Fuerza

@Entity(tableName = "estado_fuerza_table")
data class FuerzaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdFuerza") val id : Int = 0,
    @ColumnInfo(name = "oficinaR") val OficinaR : String,
    @ColumnInfo(name = "NumPunto") val numPunto : Int,
    @ColumnInfo(name = "NomPuntoRevision") val NomPuntoRevision : String,
    @ColumnInfo(name = "TipoP") val tipoP : String,
    @ColumnInfo(name = "Ubicacion") val ubicacion : String,
    @ColumnInfo(name = "CoordenadasTexto") val coordenadasT : String,
    @ColumnInfo(name = "Latitud") val latitud : Double,
    @ColumnInfo(name = "Longitud") val longitud : Double,
    @ColumnInfo(name = "PersonalINM") val personalINM : Int,
    @ColumnInfo(name = "PersonalSEDENA") val personalSedena : Int,
    @ColumnInfo(name = "PersonalMARINA") val personalMarina : Int,
    @ColumnInfo(name = "PersonalGuardiaN") val personalGuardiaN : Int,
    @ColumnInfo(name = "PersonalOTROS") val personalOtros : Int,
    @ColumnInfo(name = "Vehiculos") val vehiculos : Int,
    @ColumnInfo(name = "Seccion") val seccion : Int,
    )

fun Fuerza.toFuerzaDB() = FuerzaEntity(
    OficinaR = OficinaR,
    numPunto = numPunto,
    NomPuntoRevision = NomPuntoRevision,
    tipoP = tipoP,
    ubicacion = ubicacion,
    coordenadasT = ubicacion,
    latitud = latitud,
    longitud = longitud,
    personalINM = personalINM,
    personalSedena = personalSedena,
    personalMarina = personalMarina,
    personalGuardiaN =personalGuardiaN,
    personalOtros = personalOtros,
    vehiculos = vehiculos,
    seccion = seccion,
)
