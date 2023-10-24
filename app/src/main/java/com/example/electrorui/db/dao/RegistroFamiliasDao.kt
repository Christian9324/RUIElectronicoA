package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.IsoEntityA
import com.example.electrorui.db.entityModel.RegistroFamiliasEntity
import com.example.electrorui.db.entityModel.RegistroNombresEntity
import com.example.electrorui.usecase.model.PinFamilias

@Dao
interface RegistroFamiliasDao {

    @Query("SELECT * FROM datos_registro_familias_table")
    suspend fun getAll(): List<RegistroFamiliasEntity>

    @Query("SELECT IFNULL(COUNT(Nombre),0) AS totalFam FROM datos_registro_familias_table")
    suspend fun getTotal(): Int

    @Query("SELECT * FROM datos_registro_familias_table WHERE IdRegistroFamilia = :id")
    suspend fun getById(id : Int) : RegistroFamiliasEntity

    @Query("SELECT Nacionalidad AS nacionalidad, COUNT(Nombre) AS totales, Sexo AS sexo, Adulto as adulto, `Numero de Familia` AS familia \n" +
            "FROM datos_registro_familias_table \n" +
            "group by `Numero de Familia`,Nacionalidad, Adulto, Sexo \n" +
            "Order by `Numero de Familia` ASC, Nacionalidad DESC, Adulto DESC, Sexo DESC")
    suspend fun getDataForPin() : List<PinFamilias>

    @Query("SELECT IFNULL(max(`Numero de Familia`), 0) as fam FROM datos_registro_familias_table")
    suspend fun getNumFamilias() : Int

    @Query("SELECT * FROM datos_registro_familias_table WHERE `Numero de Familia` = :num")
    suspend fun getFamiliaByNum(num : Int) : List<RegistroFamiliasEntity>

//    @Query("SELECT iso3, COUNT(iso3) as conteo FROM datos_registro_nombres_table GROUP BY iso3")
//    suspend fun getIsoCount() : List<IsoEntityA>
//
//    @Query("SELECT * FROM datos_registro_nombres_table WHERE Nacionalidad = :nacionalidad")
//    suspend fun getByNacionalidad(nacionalidad : String) : List<RegistroFamiliasEntity>

    @Update
    suspend fun update(datosRegistroEntity: RegistroFamiliasEntity)

    @Insert
    suspend fun insert(datosRegistroEntities : List<RegistroFamiliasEntity>)

    @Delete
    suspend fun deleteEntry(datosRegistroEntity: RegistroFamiliasEntity)

    @Query("DELETE FROM datos_registro_familias_table")
    suspend fun deleteAll()

}