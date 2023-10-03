package com.example.electrorui.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.electrorui.db.entityModel.DatosRegistroEntity
import com.example.electrorui.db.entityModel.UsuarioEntity

@Dao
interface DatosRegistroDao {

    @Query("SELECT * FROM datos_registro_table")
    suspend fun getAll(): List<DatosRegistroEntity>

    @Query("SELECT * FROM datos_registro_table WHERE IdRegistro = :id")
    suspend fun getById(id : Int) : DatosRegistroEntity

    @Query("SELECT IFNULL( SUM( (ASolos_hombres + ASolos_mujeresNoEmb + ASolos_mujeresEmb + \n" +
            "AAcomp_NNAs_hombres + AAcomp_NNAs_mujeresNoEmb + AAcomp_NNAs_mujeresEmb + \n" +
            "NNAsAcomp_hombres + NNAsAcomp_mujeresNoEmb + NNAsAcomp_mujeresEmb +\n" +
            "NNAsSolos_hombres + NNAsSolos_mujeresNoEmb + NNAsSolos_mujeresEmb) ) , 0) as total \n" +
            "from datos_registro_table;")
    suspend fun getTotal(): Int

    @Update
    suspend fun update(datosRegistroEntity: DatosRegistroEntity)

    @Insert
    suspend fun insert(datosRegistroEntities : List<DatosRegistroEntity>)

    @Delete
    suspend fun deleteEntry(datosRegistroEntity: DatosRegistroEntity)

    @Query("DELETE FROM datos_registro_table")
    suspend fun deleteAll()

}