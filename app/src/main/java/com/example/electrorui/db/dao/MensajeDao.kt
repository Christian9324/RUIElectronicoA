package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.MensajeEntity
import com.example.electrorui.db.entityModel.PaisEntity

@Dao
interface MensajeDao {

    @Query("SELECT * FROM mensaje_table")
    suspend fun getAll(): List<MensajeEntity>

    @Query("SELECT * FROM mensaje_table WHERE IdMensaje = :id")
    suspend fun getById(id : Int) : MensajeEntity

    @Update
    suspend fun update(mensajeEntity: MensajeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mensajeEntity : List<MensajeEntity>)

    @Delete
    suspend fun delete(mensajeEntity: MensajeEntity)

    @Query("DELETE FROM mensaje_table")
    suspend fun deleteAll()
}