package com.example.electrorui.usecase.model

import com.example.electrorui.db.entityModel.MunicipiosEntity
import com.example.electrorui.networkApi.model.MunicipiosModel
import com.example.electrorui.networkApi.model.PaisModel

data class Municipios(
    val estado: String,
    val estadoAbr : String,
    val nomMunicipio : String,
)

fun MunicipiosModel.toUC() = Municipios(estado, estadoAbr, nomMunicipio)
fun MunicipiosEntity.toUC() = Municipios(estado, estadoAbr, nomMunicipio)
