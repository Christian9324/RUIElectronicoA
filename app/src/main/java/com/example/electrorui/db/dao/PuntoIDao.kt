package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.PaisEntity
import com.example.electrorui.db.entityModel.PuntoIEntity

@Dao
interface PuntoIDao {

    @Query("SELECT * FROM puntos_internacion_table")
    suspend fun getAll(): List<PuntoIEntity>

    @Query("SELECT * FROM puntos_internacion_table WHERE IdPuntoI = :id")
    suspend fun getById(id : Int) : PuntoIEntity

    @Query("SELECT nombrePunto FROM puntos_internacion_table WHERE tipoPunto = 'AEREOS'")
    suspend fun getByAeropuerto() : List<String>

    @Query("SELECT nombrePunto FROM puntos_internacion_table WHERE tipoPunto = 'TERRESTRES'")
    suspend fun getByTerrestres() : List<String>

    @Update
    suspend fun update(puntoI : PuntoIEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(puntoI : List<PuntoIEntity>)

    @Delete
    suspend fun delete(puntoI : PuntoIEntity)

    @Query("DELETE FROM puntos_internacion_table")
    suspend fun deleteAll()
}