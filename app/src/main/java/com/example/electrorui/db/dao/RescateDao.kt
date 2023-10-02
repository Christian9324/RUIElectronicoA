package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.RescateEntity

@Dao
interface RescateDao {

    @Query("SELECT * FROM tipo_rescate_table")
    suspend fun getAll(): List<RescateEntity>

    @Query("SELECT * FROM tipo_rescate_table WHERE IdRescate = :id")
    suspend fun getById(id : Int) : RescateEntity

    @Update
    suspend fun update(datosRescateEntity: RescateEntity)

    @Insert
    suspend fun insert(datosRescateEntities : List<RescateEntity>)

    @Delete
    suspend fun deleteEntry(datosRescateEntity: RescateEntity)

    @Query("DELETE FROM tipo_rescate_table")
    suspend fun deleteAll()

}