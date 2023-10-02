package com.example.electrorui.ui.viewModel

import androidx.core.text.HtmlCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.DelAllRegistrosUC
import com.example.electrorui.usecase.GetAllRegistrosConteoUC
import com.example.electrorui.usecase.GetAllRescatesDB
import com.example.electrorui.usecase.SetConteoRapidoCompletoAPI
import com.example.electrorui.usecase.SetConteoRapidoCompletoDB
import com.example.electrorui.usecase.SetMensajeDB
import com.example.electrorui.usecase.model.ConteoRapidoComp
import com.example.electrorui.usecase.model.Mensaje
import com.example.electrorui.usecase.model.RegistroNacionalidad
import com.example.electrorui.usecase.model.RescateComp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConteoR_AVM @Inject constructor(
    private val getAllRegistrosConteoUC: GetAllRegistrosConteoUC,
    private val delAllRegistrosUC: DelAllRegistrosUC,
    private val getAllRescatesDB: GetAllRescatesDB,
    private val setConteoRapidoCompletoDB: SetConteoRapidoCompletoDB,
    private val setConteoRapidoCompletoAPI: SetConteoRapidoCompletoAPI,
    private val setMensajeDB: SetMensajeDB,
) : ViewModel(){

    val registros by lazy { MutableLiveData<List<RegistroNacionalidad>>() }

    fun onCreate(){
        viewModelScope.launch {
            registros.value = getAllRegistrosConteoUC()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            delAllRegistrosUC()
        }
    }

    fun enviarAPI(){
        viewModelScope.launch {
            val registrosConteo = getAllRegistrosConteoUC()
            val tipoPunto = getAllRescatesDB()
            var conteoTotal = 0
            val puntoR = tipoPunto.last()
            var rescatesNac = arrayListOf<ConteoRapidoComp>()

            registrosConteo.forEach {
                rescatesNac.add(ConteoRapidoComp(puntoR, it))
                conteoTotal += (it.AS_hombres + it.AS_mujeresNoEmb + it.AS_mujeresEmb +
                        it.AA_NNAs_hombres + it.AA_NNAs_mujeresNoEmb + it.AA_NNAs_mujeresEmb +
                        it.NNAsA_hombres + it.NNAsA_mujeresNoEmb + it.NNAsA_mujeresEmb +
                        it.NNAsS_hombres + it.NNAsS_mujeresNoEmb + it.NNAsS_mujeresEmb
                        )
            }

            setConteoRapidoCompletoDB(rescatesNac)
            setConteoRapidoCompletoAPI()

            val mensajeStr = buildSpannedString {
                bold { appendLine("OR: ${puntoR.oficinaRepre}") }
                appendLine("Fecha: ${puntoR.fecha}")
                appendLine("Hora: ${puntoR.hora}")
                appendLine()

                bold { appendLine("No. de Rescatados: ${conteoTotal}") }
                appendLine()

                if (puntoR.aeropuerto == true){
                    appendLine("Aeropuerto: ${puntoR.puntoEstra}")
                    appendLine()

                } else if (puntoR.carretero == true){
                    appendLine("Carretero: ${puntoR.puntoEstra}")
                    appendLine()
                    appendLine("Tipo de vehículo: ${puntoR.tipoVehic}")
                    appendLine()
                    appendLine("Línea/empresa: ${puntoR.lineaAutobus}")
                    appendLine()
                    appendLine("No. Economico: ${puntoR.numeroEcono}")
                    appendLine()
                    appendLine("Placas: ${puntoR.placas}")
                    appendLine()
                    if (puntoR.vehiculoAseg){
                        appendLine("Vehiculo Asegurado")
                        appendLine()
                    }
                    appendLine("Municipio: ${puntoR.municipio}")
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.casaSeguridad == true){
                    appendLine("Casa de Seguridad")
                    appendLine("Municipio: ${puntoR.municipio}")
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.centralAutobus == true){
                    appendLine("Central de Autobús: ${puntoR.puntoEstra}")
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.ferrocarril == true){
                    appendLine("Ferrocarril: ${puntoR.puntoEstra}")
                    appendLine()
                    appendLine("Empresa: ${puntoR.empresa}")
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.hotel == true){
                    appendLine("Hotel")
                    appendLine("Nombre: ${puntoR.nombreHotel}")
                    appendLine("Municipio: ${puntoR.municipio}")
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.puestosADispo == true){
                    appendLine("Puestos a Disposición")
                    appendLine("Por:")
                    if (puntoR.juezCalif) {
                        appendLine("Juez Calificador")
                    }else if (puntoR.reclusorio){
                        appendLine("Reclusorio")
                    }else if (puntoR.policiaFede){
                        appendLine("Policía Federal")
                    }else if (puntoR.policiaEsta){
                        appendLine("Policía Estatal")
                    }else if (puntoR.policiaMuni){
                        appendLine("Policía Municipal")
                    }else if (puntoR.dif){
                        appendLine("DIF")
                    }else if (puntoR.fiscalia){
                        appendLine("Fiscalia")
                    }else if (puntoR.otrasAuto){
                        appendLine("Otras Autoridades")
                    }else{

                    }
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.voluntarios == true){
                    appendLine("Voluntarios")
                    appendLine()
                } else{

                }

                appendLine("Distribución por país")

                registrosConteo.forEach {
                    appendLine()
                    bold { appendLine("${it.nacionalidad}") }
                    appendLine("${it.AS_hombres} ADULTOS MASCULINOS")
                    appendLine("${it.AS_mujeresNoEmb} ADULTOS FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.AS_mujeresEmb} ADULTOS FEMENINOS EMBARAZADOS")
                    appendLine("${it.NNAsS_hombres} MENORES MASCULINOS")
                    appendLine("${it.NNAsS_mujeresNoEmb} MENORES FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.NNAsS_mujeresEmb} MENORES FEMENINOS EMBARAZADOS")
                    appendLine()
                    bold { appendLine("NÚCLEOS FAMILIARES: ${it.nucleosFamiliares}") }
                    appendLine("${it.AA_NNAs_hombres} ADULTOS MASCULINOS")
                    appendLine("${it.AA_NNAs_mujeresNoEmb} ADULTOS FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.AA_NNAs_mujeresEmb} ADULTOS FEMENINOS EMBARAZADOS")
                    appendLine("${it.NNAsA_hombres} MENORES MASCULINOS")
                    appendLine("${it.NNAsA_mujeresNoEmb} MENORES FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.NNAsA_mujeresEmb} MENORES FEMENINOS EMBARAZADOS")
                    appendLine()
                }



            }
            val mensajeDB = HtmlCompat.toHtml(mensajeStr, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)

            setMensajeDB(
                listOf(Mensaje(0, mensajeDB))
            )

        }
    }

    fun enviarDB(){
        viewModelScope.launch {
            val registrosConteo = getAllRegistrosConteoUC()
            val tipoPunto = getAllRescatesDB()
            var conteoTotal = 0
            val puntoR = tipoPunto.last()
            var rescatesNac = arrayListOf<ConteoRapidoComp>()

            registrosConteo.forEach {
                rescatesNac.add(ConteoRapidoComp(puntoR, it))
                conteoTotal += (it.AS_hombres + it.AS_mujeresNoEmb + it.AS_mujeresEmb +
                        it.AA_NNAs_hombres + it.AA_NNAs_mujeresNoEmb + it.AA_NNAs_mujeresEmb +
                        it.NNAsA_hombres + it.NNAsA_mujeresNoEmb + it.NNAsA_mujeresEmb +
                        it.NNAsS_hombres + it.NNAsS_mujeresNoEmb + it.NNAsS_mujeresEmb
                        )
            }

            setConteoRapidoCompletoDB(rescatesNac)

            val mensajeStr = buildSpannedString {
                bold { appendLine("OR: ${puntoR.oficinaRepre}") }
                appendLine("Fecha: ${puntoR.fecha}")
                appendLine("Hora: ${puntoR.hora}")
                appendLine()

                bold { appendLine("No. de Rescatados: ${conteoTotal}") }
                appendLine()

                if (puntoR.aeropuerto == true){
                    appendLine("Aeropuerto: ${puntoR.puntoEstra}")
                    appendLine()

                } else if (puntoR.carretero == true){
                    appendLine("Carretero: ${puntoR.puntoEstra}")
                    appendLine()
                    appendLine("Tipo de vehículo: ${puntoR.tipoVehic}")
                    appendLine()
                    appendLine("Línea/empresa: ${puntoR.lineaAutobus}")
                    appendLine()
                    appendLine("No. Economico: ${puntoR.numeroEcono}")
                    appendLine()
                    appendLine("Placas: ${puntoR.placas}")
                    appendLine()
                    if (puntoR.vehiculoAseg){
                        appendLine("Vehiculo Asegurado")
                        appendLine()
                    }
                    appendLine("Municipio: ${puntoR.municipio}")
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.casaSeguridad == true){
                    appendLine("Casa de Seguridad")
                    appendLine("Municipio: ${puntoR.municipio}")
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.centralAutobus == true){
                    appendLine("Central de Autobús: ${puntoR.puntoEstra}")
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.ferrocarril == true){
                    appendLine("Ferrocarril: ${puntoR.puntoEstra}")
                    appendLine()
                    appendLine("Empresa: ${puntoR.empresa}")
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.hotel == true){
                    appendLine("Hotel")
                    appendLine("Nombre: ${puntoR.nombreHotel}")
                    appendLine("Municipio: ${puntoR.municipio}")
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.puestosADispo == true){
                    appendLine("Puestos a Disposición")
                    appendLine("Por:")
                    if (puntoR.juezCalif) {
                        appendLine("Juez Calificador")
                    }else if (puntoR.reclusorio){
                        appendLine("Reclusorio")
                    }else if (puntoR.policiaFede){
                        appendLine("Policía Federal")
                    }else if (puntoR.policiaEsta){
                        appendLine("Policía Estatal")
                    }else if (puntoR.policiaMuni){
                        appendLine("Policía Municipal")
                    }else if (puntoR.dif){
                        appendLine("DIF")
                    }else if (puntoR.fiscalia){
                        appendLine("Fiscalia")
                    }else if (puntoR.otrasAuto){
                        appendLine("Otras Autoridades")
                    }else{

                    }
                    appendLine()
                    if (puntoR.presuntosDelincuentes){
                        appendLine("Presuntos Delincuentes: ${puntoR.numPresuntosDelincuentes}")
                        appendLine()
                    }
                }
                else if (puntoR.voluntarios == true){
                    appendLine("Voluntarios")
                    appendLine()
                } else{

                }

                appendLine("Distribución por país")

                registrosConteo.forEach {
                    appendLine()
                    bold { appendLine("${it.nacionalidad}") }
                    appendLine("${it.AS_hombres} ADULTOS MASCULINOS")
                    appendLine("${it.AS_mujeresNoEmb} ADULTOS FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.AS_mujeresEmb} ADULTOS FEMENINOS EMBARAZADOS")
                    appendLine("${it.NNAsS_hombres} MENORES MASCULINOS")
                    appendLine("${it.NNAsS_mujeresNoEmb} MENORES FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.NNAsS_mujeresEmb} MENORES FEMENINOS EMBARAZADOS")
                    appendLine()
                    bold { appendLine("NÚCLEOS FAMILIARES: ${it.nucleosFamiliares}") }
                    appendLine("${it.AA_NNAs_hombres} ADULTOS MASCULINOS")
                    appendLine("${it.AA_NNAs_mujeresNoEmb} ADULTOS FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.AA_NNAs_mujeresEmb} ADULTOS FEMENINOS EMBARAZADOS")
                    appendLine("${it.NNAsA_hombres} MENORES MASCULINOS")
                    appendLine("${it.NNAsA_mujeresNoEmb} MENORES FEMENINOS NO EMBARAZADOS")
                    appendLine("${it.NNAsA_mujeresEmb} MENORES FEMENINOS EMBARAZADOS")
                    appendLine()
                }



            }
            val mensajeDB = HtmlCompat.toHtml(mensajeStr, HtmlCompat.TO_HTML_PARAGRAPH_LINES_INDIVIDUAL)

            setMensajeDB(
                listOf(Mensaje(0, mensajeDB))
            )

        }
    }

}