package com.example.electrorui.networkApi

import com.example.electrorui.networkApi.model.ConteoRapidoCompModel
import com.example.electrorui.networkApi.model.FuerzaModel
import com.example.electrorui.networkApi.model.LoginModel
import com.example.electrorui.networkApi.model.MunicipiosModel
import com.example.electrorui.networkApi.model.PaisModel
import com.example.electrorui.networkApi.model.PuntosInterModel
import com.example.electrorui.networkApi.model.RescateCompModel
import com.example.electrorui.usecase.model.RespuestaA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class retrofitService @Inject constructor(
    private val retrofit: ApisServices
) {
    suspend fun verifyUser(loginData: LoginModel): LoginModel {
        return withContext(Dispatchers.IO){
            val response = retrofit.verificarUsuario(loginData)
            response
        }
    }

    suspend fun getPaises(): List<PaisModel> {
        return withContext(Dispatchers.IO){
            val response = retrofit.getAllPaises()
            response
        }
    }

    suspend fun getFuerza(): List<FuerzaModel> {
        return withContext(Dispatchers.IO){
            val response = retrofit.getAllFuerza()
            response
        }
    }

    suspend fun getMunicipios(): List<MunicipiosModel> {
        return withContext(Dispatchers.IO){
            val response = retrofit.getAllMunicipios()
            response
        }
    }

    suspend fun getPuntosInter(): List<PuntosInterModel> {
        return withContext(Dispatchers.IO){
            val response = retrofit.getAllPuntosInter()
            response
        }
    }

    suspend fun setRescates(registros : List<RescateCompModel>): RespuestaA {
        return withContext(Dispatchers.IO){
            retrofit.insertRescates(registros)
        }
    }

    suspend fun setConteos(registros : List<ConteoRapidoCompModel>): RespuestaA{
        return withContext(Dispatchers.IO){
           val response = retrofit.insertConteo(registros)
            response
        }
    }

}