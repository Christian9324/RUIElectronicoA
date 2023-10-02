package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.UsuarioEntity

@Dao
interface UsuarioDao {

    @Query("SELECT * FROM usuario_table")
    suspend fun getAll(): List<UsuarioEntity>

    @Query("SELECT * FROM usuario_table WHERE idUsuario = :id")
    suspend fun getById(id : Int) : UsuarioEntity

    @Update
    suspend fun update(usuarioEntity : UsuarioEntity)

    @Insert
    suspend fun insert(usuarioEntities : List<UsuarioEntity>)

    @Delete
    suspend fun delete(usuarioEntity : UsuarioEntity)

    @Query("DELETE FROM usuario_table")
    suspend fun deleteAll()
}