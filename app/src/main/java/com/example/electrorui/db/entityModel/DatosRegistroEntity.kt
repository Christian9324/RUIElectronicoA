package com.example.electrorui.db.entityModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.electrorui.usecase.model.RegistroNacionalidad
import java.sql.Date

@Entity(tableName = "datos_registro_table")
data class DatosRegistroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "IdRegistro") val idRegistro: Int = 0,
//    @ColumnInfo(name = "Fecha") val fecha: String,
    @ColumnInfo(name = "Nacionalidad") val nacionalidad : String,
    @ColumnInfo(name = "iso3") val iso3 : String,
    @ColumnInfo(name = "ASolos_hombres") val AS_hombres : Int,
    @ColumnInfo(name = "ASolos_mujeresNoEmb") val AS_mujeresNoEmb : Int,
    @ColumnInfo(name = "ASolos_mujeresEmb") val AS_mujeresEmb : Int,
    @ColumnInfo(name = "Nucleos_Familiares") val nucleosFamiliares : Int,
    @ColumnInfo(name = "AAcomp_NNAs_hombres") val AA_NNAs_hombres : Int,
    @ColumnInfo(name = "AAcomp_NNAs_mujeresNoEmb") val AA_NNAs_mujeresNoEmb : Int,
    @ColumnInfo(name = "AAcomp_NNAs_mujeresEmb") val AA_NNAs_mujeresEmb : Int,
    @ColumnInfo(name = "NNAsAcomp_hombres") val NNAsA_hombres : Int,
    @ColumnInfo(name = "NNAsAcomp_mujeresNoEmb") val NNAsA_mujeresNoEmb : Int,
    @ColumnInfo(name = "NNAsAcomp_mujeresEmb") val NNAsA_mujeresEmb : Int,
    @ColumnInfo(name = "NNAsSolos_hombres") val NNAsS_hombres : Int,
    @ColumnInfo(name = "NNAsSolos_mujeresNoEmb") val NNAsS_mujeresNoEmb : Int,
    @ColumnInfo(name = "NNAsSolos_mujeresEmb") val NNAsS_mujeresEmb : Int,
)

fun RegistroNacionalidad.toDB() = DatosRegistroEntity(
    nacionalidad = nacionalidad,
    iso3 = iso3,
    AS_hombres = AS_hombres,
    AS_mujeresNoEmb = AS_mujeresNoEmb,
    AS_mujeresEmb = AS_mujeresEmb,
    nucleosFamiliares = nucleosFamiliares,
    AA_NNAs_hombres = AA_NNAs_hombres,
    AA_NNAs_mujeresNoEmb = AA_NNAs_mujeresNoEmb,
    AA_NNAs_mujeresEmb = AA_NNAs_mujeresEmb,
    NNAsA_hombres = NNAsA_hombres,
    NNAsA_mujeresNoEmb = NNAsA_mujeresNoEmb,
    NNAsA_mujeresEmb = NNAsA_mujeresEmb,
    NNAsS_hombres = NNAsS_hombres,
    NNAsS_mujeresNoEmb = NNAsS_mujeresNoEmb,
    NNAsS_mujeresEmb = NNAsS_mujeresEmb,
)

fun RegistroNacionalidad.toUpdateDB() = DatosRegistroEntity(
    idRegistro = idRegistro,
    nacionalidad = nacionalidad,
    iso3 = iso3,
    AS_hombres = AS_hombres,
    AS_mujeresNoEmb = AS_mujeresNoEmb,
    AS_mujeresEmb = AS_mujeresEmb,
    nucleosFamiliares = nucleosFamiliares,
    AA_NNAs_hombres = AA_NNAs_hombres,
    AA_NNAs_mujeresNoEmb = AA_NNAs_mujeresNoEmb,
    AA_NNAs_mujeresEmb = AA_NNAs_mujeresEmb,
    NNAsA_hombres = NNAsA_hombres,
    NNAsA_mujeresNoEmb = NNAsA_mujeresNoEmb,
    NNAsA_mujeresEmb = NNAsA_mujeresEmb,
    NNAsS_hombres = NNAsS_hombres,
    NNAsS_mujeresNoEmb = NNAsS_mujeresNoEmb,
    NNAsS_mujeresEmb = NNAsS_mujeresEmb,
)
