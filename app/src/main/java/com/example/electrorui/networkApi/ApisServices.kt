package com.example.electrorui.networkApi

import com.example.electrorui.networkApi.model.ConteoRapidoCompModel
import com.example.electrorui.networkApi.model.FuerzaModel
import com.example.electrorui.networkApi.model.LoginModel
import com.example.electrorui.networkApi.model.MunicipiosModel
import com.example.electrorui.networkApi.model.PaisModel
import com.example.electrorui.networkApi.model.PuntosInterModel
import com.example.electrorui.networkApi.model.RescateCompModel
import com.example.electrorui.usecase.model.ConteoRapidoComp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApisServices {

    @POST("login/validar/")
    suspend fun verificarUsuario(@Body loginData : LoginModel) : LoginModel

    @GET("info/Paises")
    suspend fun getAllPaises() : List<PaisModel>

    @GET("info/Fuerza")
    suspend fun getAllFuerza() : List<FuerzaModel>

    @GET("info/Municipios")
    suspend fun getAllMunicipios() : List<MunicipiosModel>

    @GET("info/PuntosI")
    suspend fun getAllPuntosInter() : List<PuntosInterModel>

    @POST("registro/insertR")
    suspend fun insertRescates(@Body registros : List<RescateCompModel>)

    @POST("registro/insertC")
    suspend fun insertConteo(@Body registros : List<ConteoRapidoCompModel>)

}
