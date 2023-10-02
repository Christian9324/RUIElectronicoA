package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.ConteoRapidoCompEntity
import com.example.electrorui.db.entityModel.RescateCompEntity

@Dao
interface ConteoRapidoCompDao {

    @Query("SELECT * FROM conteo_rapido_table")
    suspend fun getAll(): List<ConteoRapidoCompEntity>

    @Update
    suspend fun update(datosEntity: ConteoRapidoCompEntity)

    @Insert
    suspend fun insert(datosEntities : List<ConteoRapidoCompEntity>)

    @Delete
    suspend fun deleteEntry(datosRegistro: ConteoRapidoCompEntity)

    @Query("DELETE FROM conteo_rapido_table")
    suspend fun deleteAll()

}