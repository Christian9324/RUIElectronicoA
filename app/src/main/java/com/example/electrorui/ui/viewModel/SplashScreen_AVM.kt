package com.example.electrorui.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.GetAllFuerzaUC
import com.example.electrorui.usecase.GetAllMunicipios
import com.example.electrorui.usecase.GetAllPaisesInitUC
import com.example.electrorui.usecase.GetAllPuntosIApi
import com.example.electrorui.usecase.GetUserUC
import com.example.electrorui.usecase.GetVerifyUserUC
import com.example.electrorui.usecase.SetDatosPendientesAPI
import com.example.electrorui.usecase.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreen_AVM @Inject constructor(
    private val getverifyUser : GetVerifyUserUC,
    private val getUser : GetUserUC,
    private val getAllPaises : GetAllPaisesInitUC,
    private val getAllMunicipios: GetAllMunicipios,
    private val getAllFuerzaUC: GetAllFuerzaUC,
    private val getAllPuntosIApi: GetAllPuntosIApi,
    private val setDatosPendientesAPI: SetDatosPendientesAPI,

    ): ViewModel() {
    val porcentProgress by lazy { MutableLiveData<Int>() }
    val continuar by lazy { MutableLiveData<Int>() }
    val conectadoInternet by lazy { MutableLiveData<Boolean>() }
    val dataUser by lazy { MutableLiveData<User>() }
    val statusMessage by lazy { MutableLiveData<String>() }
    val oficinaRepresentacion by lazy { MutableLiveData<String>() }
    val nombreUser by lazy { MutableLiveData<String>() }
    val mensajeNotif by lazy { MutableLiveData<String>() }

    fun onCreate(){
        val stadoIngreso = true
        viewModelScope.launch {

            porcentProgress.value = 10

//          Obtiene datos del usuario de la BD
            porcentProgress.value = 20
            val usuarioDatos = getUser()

            porcentProgress.value = 25
//          Teniendo conexión a internet
            if(conectadoInternet.value!!){
                try {
//                  Se Envian datos pendientes de enviar al server
                    porcentProgress.value = 40
                    mensajeNotif.value = setDatosPendientesAPI()

//                  Petición API para Verificar que el usuario loggeado tenga credenciales correctas
                    porcentProgress.value = 50
                    val infoUsuario = getverifyUser(usuarioDatos)
                    oficinaRepresentacion.value = infoUsuario.estado.toString()

//                  El usuario tiene credenciales correctas
                    if ( (!infoUsuario.password.equals("error") and !infoUsuario.password.equals(""))){

                        nombreUser.value = "${infoUsuario.nombre} ${infoUsuario.apellido}"

//                      Se descarga y guarda la información en la DB
                        getAllPaises()
                        porcentProgress.value = 80
//
                        getAllMunicipios()
                        porcentProgress.value = 90

                        getAllFuerzaUC()
                        porcentProgress.value = 95

                        getAllPuntosIApi()
                        porcentProgress.value = 100

//                      Pasa al menú de captura
                        continuar.value = 1

//                    El usuario no tiene acceso o se cambiaron sus credenciales
                    } else{
                        porcentProgress.value = 100
                        continuar.value = 2
                    }
                } catch (error : Exception){
                    Log.e("Error Conexión", error.toString())
                    statusMessage.value = "Servidor en mantenimiento"

                    if( !usuarioDatos.nickname.equals("")){
                        porcentProgress.value = 100
//                  Pasa a la captura
                        continuar.value = 1
                    } else{ //if( (usuarioDatos.password.equals("error") or usuarioDatos.password.equals("") ) ) {
                        porcentProgress.value = 100
//                  Pasa a el Loggin
                        continuar.value = 2
                    }
                }
//          No tiene Internet
            } else {
//              Se verifica que el usuario este loggeado correctamente
//              Si tiene datos correctos, pasa a captura
                if( !usuarioDatos.nickname.equals("")){
                    porcentProgress.value = 100
//                  Pasa a la captura
                    continuar.value = 1
                } else{ //if( (usuarioDatos.password.equals("error") or usuarioDatos.password.equals("") ) ) {
                    porcentProgress.value = 100
//                  Pasa a el Loggin
                    continuar.value = 2
                }
            }

        }
    }
}