package com.example.electrorui.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.electrorui.db.dao.ConteoRapidoCompDao
import com.example.electrorui.db.dao.DatosRegistroDao
import com.example.electrorui.db.dao.FuerzaDao
import com.example.electrorui.db.dao.MensajeDao
import com.example.electrorui.db.dao.MunicipiosDao
import com.example.electrorui.db.dao.PaisDao
import com.example.electrorui.db.dao.PuntoIDao
import com.example.electrorui.db.dao.RegistroFamiliasDao
import com.example.electrorui.db.dao.RegistroNombresDao
import com.example.electrorui.db.dao.RescateCompDao
import com.example.electrorui.db.dao.RescateDao
import com.example.electrorui.db.dao.UsuarioDao
import com.example.electrorui.db.entityModel.ConteoRapidoCompEntity
import com.example.electrorui.db.entityModel.DatosRegistroEntity
import com.example.electrorui.db.entityModel.FuerzaEntity
import com.example.electrorui.db.entityModel.MensajeEntity
import com.example.electrorui.db.entityModel.MunicipiosEntity
import com.example.electrorui.db.entityModel.PaisEntity
import com.example.electrorui.db.entityModel.PuntoIEntity
import com.example.electrorui.db.entityModel.RegistroFamiliasEntity
import com.example.electrorui.db.entityModel.RegistroNombresEntity
import com.example.electrorui.db.entityModel.RescateCompEntity
import com.example.electrorui.db.entityModel.RescateEntity
import com.example.electrorui.db.entityModel.UsuarioEntity

@Database(
    entities = arrayOf(
        UsuarioEntity::class,
        DatosRegistroEntity::class,
        PaisEntity::class,
        FuerzaEntity::class,
        RescateEntity::class,
        MunicipiosEntity::class,
        RegistroNombresEntity::class,
        RegistroFamiliasEntity::class,
        PuntoIEntity::class,
        RescateCompEntity::class,
        MensajeEntity::class,
        ConteoRapidoCompEntity::class,
    ),
    version = 1,
    exportSchema = false
)
abstract class AppRoomDB : RoomDatabase() {
    abstract fun getusuarioDao(): UsuarioDao
    abstract fun getdatosRegistro(): DatosRegistroDao
    abstract fun getdatosPais(): PaisDao
    abstract fun getdatosFuerza(): FuerzaDao
    abstract fun getdatosRescate(): RescateDao
    abstract fun getdatosMunicipios(): MunicipiosDao
    abstract fun getRegistroNombres(): RegistroNombresDao
    abstract fun getRegistroFamilias(): RegistroFamiliasDao
    abstract fun getPuntoI(): PuntoIDao
    abstract fun getRescateComp(): RescateCompDao
    abstract fun getMensaje(): MensajeDao
    abstract fun getConteoRapido(): ConteoRapidoCompDao
}