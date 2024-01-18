package com.example.electrorui.di

import com.example.electrorui.networkApi.ApisServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
//            .baseUrl("http://192.168.8.2:8080/")
//            .baseUrl("https://ruie.dgcvm.com/")
            .baseUrl("https://ruie.dgcor.com/")
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para la conexión
                    .readTimeout(30, TimeUnit.SECONDS)    // Tiempo de espera para leer datos
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit) : ApisServices{
        return retrofit.create(ApisServices::class.java)
    }

}