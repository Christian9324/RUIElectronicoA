package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.FuerzaEntity

@Dao
interface FuerzaDao {

    @Query("SELECT * FROM estado_fuerza_table")
    suspend fun getAll(): List<FuerzaEntity>

    @Query("SELECT DISTINCT OficinaR FROM estado_fuerza_table")
    suspend fun getAllOrs() : List<String>

    @Query("SELECT * FROM estado_fuerza_table WHERE OficinaR = :OficinaR")
    suspend fun getFuerzaByOr(OficinaR : String) : List<FuerzaEntity>

    @Query("SELECT * FROM estado_fuerza_table WHERE IdFuerza = :id")
    suspend fun getById(id : Int) : FuerzaEntity

    @Update
    suspend fun update(fuerzaEntity: FuerzaEntity)

    @Insert
    suspend fun insert(fuerzaEntityEntities : List<FuerzaEntity>)

    @Delete
    suspend fun deleteEntry(fuerzaEntityEntity: FuerzaEntity)

    @Query("DELETE FROM estado_fuerza_table")
    suspend fun deleteAll()

}