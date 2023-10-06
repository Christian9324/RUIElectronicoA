package com.example.electrorui.db

import android.content.Context
import android.content.SharedPreferences
import android.view.View

class PrefManager(context : Context?) {

    val PRIVATE_MODE = 0

    private val PREF_NAME = "MisPreferencias"
    private val IS_LOGIN = "is_login"
    private val IS_NICKNAME = "nickname"
    private val IS_USERNAME = "username"
    private val IS_STATE = "state"
    private val IS_PASS = "pass"
    private val IS_CONNECTED = "conectado"
    private val TYPE_RESC = "rescateTipo"
    private val PUNTO_REVISION = "puntoRevision"
    private val VISTAS_POPUP_INTERNET = "vistasPopUpInternet"

    val prefs: SharedPreferences? = context?.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    val editor : SharedPreferences.Editor? =  prefs?.edit()

    fun setLogin(isLogin : Boolean){
        editor?.putBoolean(IS_LOGIN, isLogin)
        editor?.commit()
    }

    fun setNickname(nickname : String){
        editor?.putString(IS_NICKNAME, nickname)
        editor?.commit()
    }

    fun setUsername(username : String){
        editor?.putString(IS_USERNAME, username)
        editor?.commit()
    }

    fun setState(state : String){
        editor?.putString(IS_STATE, state)
        editor?.commit()
    }

    fun setPass(pass : String){
        editor?.putString(IS_PASS, pass)
        editor?.commit()
    }

    fun setConnection(internet : Boolean){
        editor?.putBoolean(IS_CONNECTED, internet)
        editor?.commit()
    }

    fun setTipoRescate(tipo : Int){
        editor?.putInt(TYPE_RESC, tipo)
        editor?.commit()
    }

    fun setPuntoRevision(punto : String){
        editor?.putString(PUNTO_REVISION, punto)
        editor?.commit()
    }

    fun setvistasPopUpInternet(vistas : Boolean){
        editor?.putBoolean(VISTAS_POPUP_INTERNET, vistas)
        editor?.commit()
    }

    fun isLogin() : Boolean? = prefs?.getBoolean(IS_LOGIN, false)

    fun getNickname() : String? = prefs?.getString(IS_NICKNAME, "")

    fun getUsername() : String? = prefs?.getString(IS_USERNAME, "Juan Pérez")

    fun getState() : String? = prefs?.getString(IS_STATE,"CDMX")

    fun getPass() : String? = prefs?.getString(IS_PASS,"")

    fun getConnection() : Boolean? = prefs?.getBoolean(IS_CONNECTED, false)
    fun getTipoRescate() : Int? = prefs?.getInt(TYPE_RESC, 0)
    fun getPuntoRevision() : String? = prefs?.getString(PUNTO_REVISION, "")

    fun vistoPopUInternet() : Boolean? = prefs?.getBoolean(VISTAS_POPUP_INTERNET, false)

    fun removeData(){
        editor?.clear()
        editor?.commit()
    }

}