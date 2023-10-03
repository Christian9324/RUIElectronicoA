package com.example.electrorui.ui.viewModel

import androidx.lifecycle.MutableLiveData
import android.util.Log
import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.DelAllFamiliasUC
import com.example.electrorui.usecase.DelAllNombresUC
import com.example.electrorui.usecase.GetAeropuertos
import com.example.electrorui.usecase.GetAllFamiliasDB
import com.example.electrorui.usecase.GetAllOrs
import com.example.electrorui.usecase.GetAllPaisesUC
import com.example.electrorui.usecase.GetAllPuntosIDB
import com.example.electrorui.usecase.GetAllRegNombresDB
import com.example.electrorui.usecase.GetDataPinFamilias
import com.example.electrorui.usecase.GetDataPinNombres
import com.example.electrorui.usecase.GetFuerzaByOrUC
import com.example.electrorui.usecase.GetInfoMasivo
import com.example.electrorui.usecase.GetMunicipiosByOR
import com.example.electrorui.usecase.GetRegistrosByIsoCountUC
import com.example.electrorui.usecase.GetTotalFamilias
import com.example.electrorui.usecase.SetMensajeDB
import com.example.electrorui.usecase.SetRescateAPIUC
import com.example.electrorui.usecase.SetRescateCompletoDB
import com.example.electrorui.usecase.SetTipoRescateDB
import com.example.electrorui.usecase.model.Fuerza
import com.example.electrorui.usecase.model.Iso
import com.example.electrorui.usecase.model.Mensaje
import com.example.electrorui.usecase.model.Municipios
import com.example.electrorui.usecase.model.PuntosInter
import com.example.electrorui.usecase.model.RegistroFamilias
import com.example.electrorui.usecase.model.Rescate
import com.example.electrorui.usecase.model.RescateComp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Captura_FVM @Inject constructor(
    private val getAllPaisesUC: GetAllPaisesUC,
    private val getRegistrosByIsoCountUC: GetRegistrosByIsoCountUC,
    private val getAllOrs: GetAllOrs,
    private val getMunicipiosByOR: GetMunicipiosByOR,
    private val getFuerzaByOrUC: GetFuerzaByOrUC,
    private val getTotalFamilias: GetTotalFamilias,
    private val getAllPuntosIDB: GetAllPuntosIDB,
    private val getAllRegistroFamiliasDB: GetAllFamiliasDB,
    private val getAllRegNombresDB: GetAllRegNombresDB,
    private val getDataPinNombres: GetDataPinNombres,
    private val getDataPinFamilias: GetDataPinFamilias,
    private val getInfoMasivo: GetInfoMasivo,
    private val delAllFamiliasUC: DelAllFamiliasUC,
    private val delAllNombresUC: DelAllNombresUC,
    private val setMensajeDB: SetMensajeDB,
    private val setTipoRescateDB: SetTipoRescateDB,
    private val setRescateAPIUC: SetRescateAPIUC,
    private val setRescateC: SetRescateCompletoDB,
) : ViewModel(){

    val oficinaRepresentacion by lazy { MutableLiveData<String>() }

    val datosBRescate by lazy { MutableLiveData<Rescate>() }

    val numFamilia by lazy { MutableLiveData<Int>() }
    val paises by lazy { MutableLiveData<List<String>>() }
    val iso3 by lazy { MutableLiveData<List<String>>() }
    val datosIso by lazy { MutableLiveData<List<Iso>>() }
    val numerosFamilias by lazy { MutableLiveData<List<Int>>() }
    val oficinas by lazy { MutableLiveData<List<String>>() }
    val municipiosNom by lazy { MutableLiveData<List<String>>() }
    val municipios by lazy { MutableLiveData<List<Municipios>>() }
    val puntoRescateNom by lazy { MutableLiveData<List<String>>() }
    val puntoRescate by lazy { MutableLiveData<List<Fuerza>>() }
    val puntoInter by lazy { MutableLiveData<List<PuntosInter>>() }
    val noRescatados by lazy { MutableLiveData<Int>() }
    val masivo by lazy { MutableLiveData<Boolean>() }
    val mensaje by lazy { MutableLiveData<String>() }
    val pasarVentana by lazy { MutableLiveData<Boolean>() }
    val etPuntoRescate = MutableLiveData<String>()


    fun onCreate(){
        viewModelScope.launch {
            val paisesDB = getAllPaisesUC()
            paises.value = paisesDB.map { x -> x.pais }
            iso3.value = paisesDB.map { x -> x.iso3 }

            datosIso.value = getRegistrosByIsoCountUC()
            oficinas.value = getAllOrs()

            val totalFam = getTotalFamilias()
            numFamilia.value = totalFam + 1

            numerosFamilias.value = List(totalFam){ it + 1}
//            numFamilia.value = 1
            val totalRegistros = getInfoMasivo()
            noRescatados.value = totalRegistros
            masivo.value = totalRegistros >= 40

        }
    }

    fun onResume(){
        viewModelScope.launch {

            val totalFam = getTotalFamilias()
            numFamilia.value = totalFam + 1

            datosIso.value = getRegistrosByIsoCountUC()
            numerosFamilias.value = List(totalFam){ it + 1}
//            numFamilia.value = 1
            val totalRegistros = getInfoMasivo()
            noRescatados.value = totalRegistros
            masivo.value = totalRegistros >= 40

        }
    }

    fun dataAditional(oficnaR : String){
        viewModelScope.launch {
            oficinaRepresentacion.value = oficnaR
            val auxMun = getMunicipiosByOR(oficnaR)
            municipios.value = auxMun
            municipiosNom.value = auxMun.map { it.nomMunicipio }

//            val auxRN = getFuerzaByOrUC(oficnaR)
//            val auxRN = getFuerzaByOrUC(oficinaRepresentacion.value)
//            puntoRescate.value = auxRN
//            puntoRescateNom.value = auxRN.map { it.NomPuntoRevision }

            val auxPI = getAllPuntosIDB()
            puntoInter.value = auxPI

        }
    }

    fun buscarAeropuertos(){
        viewModelScope.launch {
            val puntosTotales = puntoInter.value
            var aeropuertos = ArrayList<String>()
            puntosTotales?.forEach {
                if( it.estadoPunto.equals(oficinaRepresentacion.value) and it.tipoPunto.equals("AEREOS") ){
                    aeropuertos.add(it.nombrePunto)
                }
            }
            puntoRescateNom.value = aeropuertos
        }
    }

    fun buscarCarretero(){
        viewModelScope.launch {
//            Obtenemos los puntos del estado de fuerza coincidente con carreteros
            val auxRN = getFuerzaByOrUC(oficinaRepresentacion.value!!)
            var carreteros = ArrayList<String>()
            auxRN.forEach {
                if (it.tipoP.equals("Carretero"))
                    carreteros.add(it.NomPuntoRevision)
            }

//            Obtenemos los puntos terrestres de los puntos de internacion y se agregan los datos
            val auxPI = puntoInter.value
            auxPI?.forEach {
                if( it.estadoPunto.equals(oficinaRepresentacion.value) and it.tipoPunto.equals("TERRESTRES")){
                    carreteros.add(it.nombrePunto)
                }
            }
            puntoRescateNom.value = carreteros
        }
    }

    fun buscarEstacionAuto(){
        viewModelScope.launch {
            val auxRN = getFuerzaByOrUC(oficinaRepresentacion.value!!)
            var estacionBus = ArrayList<String>()


            auxRN.forEach {
                if (it.tipoP.equals("Central de autobús"))
                    estacionBus.add(it.NomPuntoRevision)
            }
            puntoRescateNom.value = estacionBus
        }
    }

    fun buscarOtros(){
        viewModelScope.launch {
            val auxRN = getFuerzaByOrUC(oficinaRepresentacion.value!!)
            var otros = ArrayList<String>()
            auxRN.forEach {
                if (it.tipoP.equals("Otro"))
                    otros.add(it.NomPuntoRevision)
            }
            puntoRescateNom.value = otros
        }
    }

    fun saveTipoRescate(){
        viewModelScope.launch {
            setTipoRescateDB(listOf(datosBRescate.value!!) )
        }
    }

    fun GuardarRescateFAPI(){
        viewModelScope.launch {
            val regF = getAllRegistroFamiliasDB()
            val regN = getAllRegNombresDB()
            val datosRescatePuntoC = datosBRescate.value

//            Log.e("datos Rescate", datosRescatePuntoC.toString())
//            Log.e("datos regF", regF.toString())
//            Log.e("datos regN", regN.toString())

            var resc_y_Nombres = arrayListOf<RescateComp>()

            regF.forEach {
                resc_y_Nombres.add(RescateComp(datosRescatePuntoC!!, it))
            }

            regN.forEach {
                resc_y_Nombres.add(RescateComp(datosRescatePuntoC!!, it))
            }
            setRescateAPIUC(resc_y_Nombres)
        }
    }

    fun GuardarRescateDB(){
        viewModelScope.launch {
            val regF = getAllRegistroFamiliasDB()
            val regN = getAllRegNombresDB()
            val datosRescatePuntoC = datosBRescate.value

            var resc_y_Nombres = arrayListOf<RescateComp>()

            regF.forEach {
                resc_y_Nombres.add(RescateComp(datosRescatePuntoC!!, it))
            }

            regN.forEach {
                resc_y_Nombres.add(RescateComp(datosRescatePuntoC!!, it))
            }
            setRescateC(resc_y_Nombres)
        }
    }

    fun delAllDatos(){
        viewModelScope.launch {
            delAllFamiliasUC()
            delAllNombresUC()
        }
    }

    fun createPin(){
        viewModelScope.launch {
            val infoPunto = datosBRescate.value
            val infoPinNombres = getDataPinNombres()
            val infoPinFamilias = getDataPinFamilias()
            val noRescatesTotales = noRescatados.value
            val totalFam = getTotalFamilias()

            if(noRescatesTotales!! > 0){
                val mensajeStr = buildSpannedString {
                    bold { appendLine("OR: ${infoPunto?.oficinaRepre}") }
                    appendLine("Fecha: ${infoPunto?.fecha}")
                    appendLine("Hora: ${infoPunto?.hora}")
                    appendLine()

                    bold { appendLine("No. de Rescatados: ${noRescatesTotales}") }
                    appendLine()

                    if (infoPunto?.aeropuerto == true){
                        appendLine("Aeropuerto: ${infoPunto.puntoEstra}")
                        appendLine()

                    } else if (infoPunto?.carretero == true){
                        appendLine("Carretero: ${infoPunto.puntoEstra}")
                        appendLine()
                        appendLine("Tipo de vehículo: ${infoPunto.tipoVehic}")
                        appendLine()
                        appendLine("Línea/empresa: ${infoPunto.lineaAutobus}")
                        appendLine()
                        appendLine("No. Economico: ${infoPunto.numeroEcono}")
                        appendLine()
                        appendLine("Placas: ${infoPunto.placas}")
                        appendLine()
                        if (infoPunto.vehiculoAseg){
                            appendLine("Vehiculo Asegurado")
                            appendLine()
                        }
                        appendLine("Municipio: ${infoPunto.municipio}")
                        appendLine()
                        if (infoPunto.presuntosDelincuentes){
                            appendLine("Presuntos Delincuentes: ${infoPunto.numPresuntosDelincuentes}")
                            appendLine()
                        }
                    }
                    else if (infoPunto?.casaSeguridad == true){
                        appendLine("Casa de Seguridad")
                        appendLine("Municipio: ${infoPunto.municipio}")
                        if (infoPunto.presuntosDelincuentes){
                            appendLine("Presuntos Delincuentes: ${infoPunto.numPresuntosDelincuentes}")
                            appendLine()
                        }
                    }
                    else if (infoPunto?.centralAutobus == true){
                        appendLine("Central de Autobús: ${infoPunto.puntoEstra}")
                        appendLine()
                        if (infoPunto.presuntosDelincuentes){
                            appendLine("Presuntos Delincuentes: ${infoPunto.numPresuntosDelincuentes}")
                            appendLine()
                        }
                    }
                    else if (infoPunto?.ferrocarril == true){
                        appendLine("Ferrocarril: ${infoPunto.puntoEstra}")
                        appendLine()
                        appendLine("Empresa: ${infoPunto.empresa}")
                        appendLine()
                        if (infoPunto.presuntosDelincuentes){
                            appendLine("Presuntos Delincuentes: ${infoPunto.numPresuntosDelincuentes}")
                            appendLine()
                        }
                    }
                    else if (infoPunto?.hotel == true){
                        appendLine("Hotel")
                        appendLine("Nombre: ${infoPunto.nombreHotel}")
                        appendLine("Municipio: ${infoPunto.municipio}")
                        if (infoPunto.presuntosDelincuentes){
                            appendLine("Presuntos Delincuentes: ${infoPunto.numPresuntosDelincuentes}")
                            appendLine()
                        }
                    }
                    else if (infoPunto?.puestosADispo == true){
                        appendLine("Puestos a Disposición")
                        appendLine("Por:")
                        if (infoPunto.juezCalif) {
                            appendLine("Juez Calificador")
                        }else if (infoPunto.reclusorio){
                            appendLine("Reclusorio")
                        }else if (infoPunto.policiaFede){
                            appendLine("Policía Federal")
                        }else if (infoPunto.policiaEsta){
                            appendLine("Policía Estatal")
                        }else if (infoPunto.policiaMuni){
                            appendLine("Policía Municipal")
                        }else if (infoPunto.dif){
                            appendLine("DIF")
                        }else if (infoPunto.fiscalia){
                            appendLine("Fiscalia")
                        }else if (infoPunto.otrasAuto){
                            appendLine("Otras Autoridades")
                        }else{

                        }
                        appendLine()
                        if (infoPunto.presuntosDelincuentes){
                            appendLine("Presuntos Delincuentes: ${infoPunto.numPresuntosDelincuentes}")
                            appendLine()
                        }
                    }
                    else if (infoPunto?.voluntarios == true){
                        appendLine("Voluntarios")
                        appendLine()
                    } else{

                    }
                    var nacioBase = ""
                    var familiaBase = 0
                    if (!infoPinNombres.isEmpty()){
                        for (i in infoPinNombres){
                            if (!i.nacionalidad.equals(nacioBase)){
                                appendLine()
                                bold { appendLine("${i.nacionalidad}") }
                                nacioBase = i.nacionalidad

                                if (i.adulto and i.sexo){
                                    appendLine("${i.totales} ADULTO(S) MASCULINO(S)")
                                }else if (i.adulto and !i.sexo){
                                    appendLine("${i.totales} ADULTOS FEMENINO(S)")
                                }else if (!i.adulto and i.sexo){
                                    appendLine("${i.totales} MENOR(ES) MASCULINO(S)")
                                }else if (!i.adulto and !i.sexo){
                                    appendLine("${i.totales} MENOR(ES) FEMENINO(S)")
                                }

                            } else{
                                if (i.adulto and i.sexo){
                                    appendLine("${i.totales} ADULTO(S) MASCULINO(S)")
                                }else if (i.adulto and !i.sexo){
                                    appendLine("${i.totales} ADULTO(S) FEMENINO(S)")
                                }else if (!i.adulto and i.sexo){
                                    appendLine("${i.totales} MENOR(ES) MASCULINO(S)")
                                }else if (!i.adulto and !i.sexo){
                                    appendLine("${i.totales} MENOR(ES) FEMENINO(S)")
                                }

                            }
                        }
                    }

                    if (totalFam > 0){
                        appendLine()
                        bold { appendLine("NÚCLEOS FAMILIARES ${totalFam}") }

                        if (!infoPinFamilias.isEmpty()){
                            for (i in infoPinFamilias){
                                if (i.familia != familiaBase){
                                    appendLine()
                                    bold { appendLine("NUCLEO #${i.familia}") }
                                    familiaBase = i.familia

                                    if (i.adulto and i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} ADULTO(S) MASCULINO(S) ${i.nacionalidad}")
                                    }else if (i.adulto and !i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} ADULTO(S) FEMENINO(S) ${i.nacionalidad}")
                                    }else if (!i.adulto and i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} MENOR(ES) MASCULINO(S) ${i.nacionalidad}")
                                    }else if (!i.adulto and !i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} MENOR(ES) FEMENINO(S) ${i.nacionalidad}")
                                    }

                                } else{
                                    if (i.adulto and i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} ADULTO(S) MASCULINO(S) ${i.nacionalidad}")
                                    }else if (i.adulto and !i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} ADULTO(S) FEMENINO(S) ${i.nacionalidad}")
                                    }else if (!i.adulto and i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} MENOR(ES) MASCULINO(S) ${i.nacionalidad}")
                                    }else if (!i.adulto and !i.sexo){
                                        if(i.totales > 0) appendLine("${i.totales} MENOR(ES) FEMENINO(S) ${i.nacionalidad}")
                                    }

                                }
                            }
                        }
                    }
                }

//            mensaje.value = Html.toHtml(mensajeStr)
                mensaje.value = HtmlCompat.toHtml(mensajeStr, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)
//            Log.e("Datos", mensaje.value!!)
                setMensajeDB(
                    listOf(Mensaje(0, mensaje.value!! ))
                )
            }
        }
    }
    fun savePin(){
        viewModelScope.launch {
            setMensajeDB(
                listOf(Mensaje(0, mensaje.value!! ))
            )
        }
    }

}

//val fechaC by lazy { MutableLiveData<String>() }
//    val horaC by lazy { MutableLiveData<String>() }
//    val aeropuertoC by lazy { MutableLiveData<Boolean>() }
//    val carreteroC by lazy { MutableLiveData<String>() }
//    val tipoVehicC by lazy { MutableLiveData<String>() }
//    val lineaAutobusC by lazy { MutableLiveData<String>() }
//    val numeroEconoC by lazy { MutableLiveData<String>() }
//    val placasC by lazy { MutableLiveData<String>() }
//    val vehiculoAsegC by lazy { MutableLiveData<Boolean>() }
//    val casaSeguridadC by lazy { MutableLiveData<Boolean>() }
//    val centralAutobusC by lazy { MutableLiveData<Int>() }
//    val ferrocarrilC by lazy { MutableLiveData<Boolean>() }
//    val empresaC by lazy { MutableLiveData<String>() }
//    val hotelC by lazy { MutableLiveData<Boolean>() }
//    val nombreHotelC by lazy { MutableLiveData<String>() }
//    val puestosADispoC by lazy { MutableLiveData<Boolean>() }
//    val juezCalifC by lazy { MutableLiveData<Boolean>() }
//    val reclusorioC by lazy { MutableLiveData<Boolean>() }
//    val policiaFedeC by lazy { MutableLiveData<Boolean>() }
//    val difC by lazy { MutableLiveData<Boolean>() }
//    val policiaEstaC by lazy { MutableLiveData<Boolean>() }
//    val policiaMuniC by lazy { MutableLiveData<Boolean>() }
//    val guardiaNaciC by lazy { MutableLiveData<Boolean>() }
//    val fiscaliaC by lazy { MutableLiveData<Boolean>() }
//    val otrasAutoC by lazy { MutableLiveData<Boolean>() }
//    val voluntariosC by lazy { MutableLiveData<Boolean>() }
//    val presuntosDelincuentesC by lazy { MutableLiveData<Boolean>() }
//    val numPresuntosDelincuentesC by lazy { MutableLiveData<Int>() }
//    val municipioC by lazy { MutableLiveData<String>() }
//    val puntoEstraC by lazy { MutableLiveData<String>() }