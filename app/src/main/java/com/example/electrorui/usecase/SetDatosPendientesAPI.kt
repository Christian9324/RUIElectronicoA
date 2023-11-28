package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.RescateComp
import javax.inject.Inject

class SetDatosPendientesAPI @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): String {
        val registrosConteo = repository.getAllDataConteoRapidoFromDB()
        val registrosCompleto = repository.getAllRescateCompletoFromDB()
        var mensajeF = ""
        try {
            if ( !registrosConteo.isNullOrEmpty()) {
//                Log.e("info Conteo Api", "Entro a funcion")
                val respuestaC = repository.insertConteosFromApi(registrosConteo)

                if (respuestaC.respuestaAPI.equals("ok")){
                    repository.deleteAllDataConteoRapidoFromDB()
                    mensajeF += "Los datos almacenados sin Conexion de Conteo Rapido se ENVIARON\n"
                } else {
                    mensajeF += "Conteo Rapido No se pudo enviar\n"
                }
            }
            if ( !registrosCompleto.isNullOrEmpty()) {
//                Log.e("info Rescate Api", "Entro a funcion")
                val respuestaR = repository.insertRescatesFromApi(registrosCompleto)

                if (respuestaR.respuestaAPI.equals("ok")){
                    repository.deleteAllRescateCompletoFromDB()
                    mensajeF += "Los datos almacenados sin Conexion de Rescates se ENVIARON\n"
                } else {
                    mensajeF += "Los Rescates NO se pudieron enviar\n"
                }
            }
        }catch (e : Exception){
            Log.e("error al insertar API", e.toString())
            mensajeF += "Error al enviar ${e.toString()}"
        }

        return mensajeF
    }
}