package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.PaisEntity

@Dao
interface PaisDao {

    @Query("SELECT * FROM pais_table")
    suspend fun getAll(): List<PaisEntity>

    @Query("SELECT * FROM pais_table WHERE IdPais = :id")
    suspend fun getById(id : Int) : PaisEntity

    @Update
    suspend fun update(paisEntity : PaisEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paisEntities : List<PaisEntity>)

    @Delete
    suspend fun delete(paisEntity : PaisEntity)

    @Query("DELETE FROM pais_table")
    suspend fun deleteAll()
}