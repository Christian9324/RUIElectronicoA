package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.IsoEntityA
import com.example.electrorui.db.entityModel.RegistroNombresEntity
import com.example.electrorui.usecase.model.PinNacionalidad

@Dao
interface RegistroNombresDao {

    @Query("SELECT * FROM datos_registro_nombres_table")
    suspend fun getAll(): List<RegistroNombresEntity>

    @Query("SELECT IFNULL(COUNT(Nombre),0) AS totalNom FROM datos_registro_nombres_table")
    suspend fun getTotal(): Int

    @Query("SELECT * FROM datos_registro_nombres_table WHERE IdRegistroNom = :id")
    suspend fun getById(id : Int) : RegistroNombresEntity

    @Query("SELECT iso3, COUNT(iso3) as conteo FROM datos_registro_nombres_table GROUP BY iso3")
    suspend fun getIsoCount() : List<IsoEntityA>

    @Query("SELECT * FROM datos_registro_nombres_table WHERE Nacionalidad = :nacionalidad")
    suspend fun getByNacionalidad(nacionalidad : String) : List<RegistroNombresEntity>

    @Query("SELECT Nacionalidad AS nacionalidad, COUNT(Nombre) AS totales, Sexo AS sexo, Adulto AS adulto \n" +
            "FROM datos_registro_nombres_table \n" +
            "group by Nacionalidad, Adulto, Sexo \n" +
            "Order by Adulto DESC, Sexo DESC")
    suspend fun getDataForPin() : List<PinNacionalidad>

    @Update
    suspend fun update(datosRegistroEntity: RegistroNombresEntity)

    @Insert
    suspend fun insert(datosRegistroEntities : List<RegistroNombresEntity>)

    @Delete
    suspend fun deleteEntry(datosRegistroEntity: RegistroNombresEntity)

    @Query("DELETE FROM datos_registro_nombres_table")
    suspend fun deleteAll()

}