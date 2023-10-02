package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.MunicipiosEntity

@Dao
interface MunicipiosDao {

    @Query("SELECT * FROM municipios_table")
    suspend fun getAll(): List<MunicipiosEntity>

    @Query("SELECT * FROM municipios_table WHERE IdMunicipios = :id")
    suspend fun getById(id : Int) : MunicipiosEntity

    @Query("SELECT DISTINCT estado from municipios_table")
    suspend fun getOrs() : List<String>

    @Query("SELECT * from municipios_table WHERE estado = :oficinaR")
    suspend fun getMunByOr(oficinaR : String) : List<MunicipiosEntity>

    @Update
    suspend fun update(datosMunicipiosEntity: MunicipiosEntity)

    @Insert
    suspend fun insert(datosMunicipiosEntities : List<MunicipiosEntity>)

    @Delete
    suspend fun deleteEntry(datosMunicipiosEntity: MunicipiosEntity)

    @Query("DELETE FROM municipios_table")
    suspend fun deleteAll()

}