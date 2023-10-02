package com.example.electrorui.di

import android.content.Context
import androidx.room.Room
import com.example.electrorui.db.AppRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "app_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppRoomDB::class.java,
            DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideUsuarioDao(db : AppRoomDB) = db.getusuarioDao()

    @Singleton
    @Provides
    fun provideDatosRegistroDao(db : AppRoomDB) = db.getdatosRegistro()

    @Singleton
    @Provides
    fun providePaisDao(db : AppRoomDB) = db.getdatosPais()

    @Singleton
    @Provides
    fun provideFuerzaDao(db : AppRoomDB) = db.getdatosFuerza()

    @Singleton
    @Provides
    fun provideRescateDao(db : AppRoomDB) = db.getdatosRescate()

    @Singleton
    @Provides
    fun provideMunicipiosDao(db : AppRoomDB) = db.getdatosMunicipios()

    @Singleton
    @Provides
    fun provideRegistroNombresDao(db : AppRoomDB) = db.getRegistroNombres()

    @Singleton
    @Provides
    fun provideRegistroFamiliasDao(db : AppRoomDB) = db.getRegistroFamilias()

    @Singleton
    @Provides
    fun providePuntoIDao(db : AppRoomDB) = db.getPuntoI()

    @Singleton
    @Provides
    fun provideRescateCompDao(db : AppRoomDB) = db.getRescateComp()

    @Singleton
    @Provides
    fun provideMensajeDao(db : AppRoomDB) = db.getMensaje()

    @Singleton
    @Provides
    fun provideConteoRapidoCompDao(db : AppRoomDB) = db.getConteoRapido()

}