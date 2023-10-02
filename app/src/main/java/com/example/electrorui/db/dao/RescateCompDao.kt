package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.RescateCompEntity

@Dao
interface RescateCompDao {

    @Query("SELECT * FROM rescate_completo_table")
    suspend fun getAll(): List<RescateCompEntity>

    @Update
    suspend fun update(datosRegistroEntity: RescateCompEntity)

    @Insert
    suspend fun insert(datosRegistroEntities : List<RescateCompEntity>)

    @Delete
    suspend fun deleteEntry(datosRegistroEntity: RescateCompEntity)

    @Query("DELETE FROM rescate_completo_table")
    suspend fun deleteAll()

}