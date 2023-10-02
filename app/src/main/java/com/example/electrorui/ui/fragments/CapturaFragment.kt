package com.example.electrorui.ui.fragments


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.electrorui.R
import com.example.electrorui.ui.RescateFamiliasActivity
import com.example.electrorui.ui.RescateNombresActivity
import com.example.electrorui.databinding.ActivityPopupCarreteroBinding
import com.example.electrorui.databinding.ActivityPopupEnviarBinding
import com.example.electrorui.databinding.FragmentCapturaBinding
import com.example.electrorui.databinding.ToastLayoutErrorBinding
import com.example.electrorui.ui.ConteoRActivity
import com.example.electrorui.ui.adapters.FamiliaAdapter
import com.example.electrorui.ui.adapters.IsoAdapter
import com.example.electrorui.ui.viewModel.Captura_FVM
import com.example.electrorui.db.PrefManager
import com.example.electrorui.usecase.model.Rescate
import com.example.electrorui.usecase.model.TipoRescate
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class CapturaFragment : Fragment() {
    companion object{
        val EXTRA_REGISTRO_NUEVO = "CapturaFragment:registroNuevo"
        val EXTRA_REGISTRO = "CapturaFragment:registro"
    }

    private var _binding: FragmentCapturaBinding? = null
    private val dataActivityViewM : Captura_FVM by viewModels()
    private val binding get() = _binding!!
    private var isConnected : Boolean = false
    private var vistoMensajeInternet : Boolean = false
    private var municipiosNom : List<String> = emptyList()
    private var puntoRescateNom : List<String> = emptyList()
    private lateinit var nacionalidadesAdapter : IsoAdapter
    private lateinit var familiaAdapter : FamiliaAdapter
    private lateinit var prefManager: PrefManager
    private var dataRescateP = TipoRescate()
    private lateinit var icon : Drawable

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {


        _binding = FragmentCapturaBinding.inflate(inflater, container, false)

        prefManager = PrefManager(requireContext())

//        activity?.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        requireActivity().actionBar?.hide()
//        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
//        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        icon = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_error_24)!!
        DrawableCompat.setTint(icon, Color.parseColor("#FFFF00"))
        icon.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)

        isConnected = prefManager.getConnection()!!
        vistoMensajeInternet = prefManager.vistoPopUInternet()!!
        if (!isConnected and !vistoMensajeInternet){
//          Dialogo para la alerta de ingresos sin internet
            popUpInternet()
            prefManager.setvistasPopUpInternet(true)
        }

// -------- Floating Button Conteo Rapido ----------------
        binding.fbConteoRapido.setOnClickListener {
            verifyData()
            dataActivityViewM.saveTipoRescate()
            startActivity(Intent(requireContext(),ConteoRActivity::class.java))
        }

        dataActivityViewM.oficinas.observe(viewLifecycleOwner){
            val oficinas = it
            val estado = prefManager.getState()!!.toInt()

            val oficina = oficinas[estado-1]
            binding.textViewOR.setText("OR: ${oficina}")
            dataActivityViewM.dataAditional(oficina)
        }

        dataActivityViewM.puntoRescateNom.observe(viewLifecycleOwner){
            puntoRescateNom = it
        }

        dataActivityViewM.etPuntoRescate.observe(viewLifecycleOwner){
            binding.spinnerPuntoR.setText(it)
        }

        dataActivityViewM.municipiosNom.observe(viewLifecycleOwner){
            municipiosNom = it
        }

        dataActivityViewM.masivo.observe(viewLifecycleOwner){
            if(it){
                binding.tvRescateMasivo.visibility = View.VISIBLE
            } else {
                binding.tvRescateMasivo.visibility = View.GONE
            }
        }

        val adapterSpinner = ArrayAdapter.createFromResource(
                requireActivity().applicationContext,
                R.array.tipo_rescate,
                android.R.layout.simple_spinner_item
            )
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.spinnerTipo.setAdapter(adapterSpinner)
        binding.spinnerTipo.setSelection(prefManager.getTipoRescate()!!,false)

        var dataTipoRescate = emptyList<String>()
        val spinnerTipoRadapter = ArrayAdapter(requireActivity()
            .applicationContext, android.R.layout.simple_spinner_item, dataTipoRescate)
        binding.spinnerPuntoR.threshold = 1
        binding.spinnerPuntoR.setAdapter(spinnerTipoRadapter)

//---------------- spinner de Selección de Tipo de Punto de Rescate-------------
        binding.spinnerPuntoR.setOnItemClickListener { adapterView, view, i, l ->
            hideKeyboard()
            val textoPuntoR = binding.spinnerPuntoR.text.toString()
            dataActivityViewM.etPuntoRescate.value = textoPuntoR
            prefManager.setPuntoRevision(textoPuntoR)
            prefManager.setVisibilidadPuntoRevision(true)
        }

        binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())

        dataActivityViewM.puntoRescateNom.observe(viewLifecycleOwner){
            binding.spinnerPuntoR.setAdapter(
                ArrayAdapter(
                    requireActivity().applicationContext,
                    android.R.layout.simple_spinner_dropdown_item,
                    it)
            )
            spinnerTipoRadapter.notifyDataSetChanged()
        }

//---------------Spinner seleccion de Punto de rescate -------------------
        binding.spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if ( p2 > 0) {
                    //              Se Coloca en 0 (default) el spinner de Tipo de Rescate
                    binding.spinnerTipo.setSelection(0)
                    binding.spinnerPuntoR.setText("")
                    prefManager.setPuntoRevision("")
                    prefManager.setVisibilidadPuntoRevision(false)
                    prefManager.setTipoRescate(0)

//               Si no esta colocada la hora, no permite avanzar
                    if (binding.editTextHora.text.isEmpty()){
                        binding.editTextHora.setError("llenar para continuar", icon)
                        binding.spinnerPuntoR.visibility = View.GONE
                        showToastError("Ingresa la hora primero", Toast.LENGTH_LONG)

                    }
                    else {
//                  Colocada la hora se quita el simbolo de error
                        binding.editTextHora.error = null
//                  Se guarda el estado del spinner a la posición seleccionada por el usuario
                        prefManager.setTipoRescate(p2!!)

//                  Se coloca vacio el campo de Punto estrategico o (Punto de Revision)
                        dataActivityViewM.etPuntoRescate.value = ""
//                  Se guarda como invisible el ET de Punto Estrategico
                        prefManager.setVisibilidadPuntoRevision(false)
                        when(p2){
                            //                    ----- Aeropuerto ------
                            1 -> {
//                          Se asigna al spinner la opción que el usuario presiono
                                binding.spinnerTipo.setSelection(p2)
//                          Se crea un tipo de rescate con los valores por defecto
                                dataRescateP = TipoRescate()
//                          Se guardan los datos predeterminados para aeropuerto
                                dataRescateP.aeropuerto = true
                                binding.spinnerPuntoR.visibility = View.VISIBLE
                                dataActivityViewM.buscarAeropuertos()
                            }
                            //                   ----- Carretero ------
                            2 -> {
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.carretero = true
                                binding.spinnerPuntoR.visibility = View.VISIBLE
                                prefManager.setVisibilidadPuntoRevision(true)
                                showPopUp1(p0!!.getItemAtPosition(p2).toString())
                                dataActivityViewM.buscarCarretero()
                            }
                            //                    ----- Casa de seguridad ------
                            3 ->{
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.casaSeguridad = true
                                binding.spinnerPuntoR.visibility = View.GONE
                                prefManager.setVisibilidadPuntoRevision(false)
                                showPopUp1(p0!!.getItemAtPosition(p2).toString())
                            }
                            //                    ----- Central de Autobus ------
                            4 -> {
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.centralAutobus = true
                                binding.spinnerPuntoR.visibility = View.VISIBLE
                                prefManager.setVisibilidadPuntoRevision(true)
                                dataActivityViewM.buscarEstacionAuto()
                            }
                            //                    ----- Ferrocarril ------
                            5 -> {
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.ferrocarril = true
                                binding.spinnerPuntoR.visibility = View.VISIBLE
                                prefManager.setVisibilidadPuntoRevision(true)
                                showPopUp1(p0!!.getItemAtPosition(p2).toString())
                            }
                            //                    ----- Hotel ------
                            6 ->{
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.hotel = true
                                binding.spinnerPuntoR.visibility = View.GONE
                                prefManager.setVisibilidadPuntoRevision(false)
                                showPopUp1(p0!!.getItemAtPosition(p2).toString())
                            }
                            //                    ----- Puestos a Disposición ------
                            7 ->{
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.puestosADispo = true
                                binding.spinnerPuntoR.visibility = View.GONE
                                prefManager.setVisibilidadPuntoRevision(false)
                                showPopUp1(p0!!.getItemAtPosition(p2).toString())
                            }
                            //                    ----- Voluntarios ------
                            8->{
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.voluntarios = true
                                binding.spinnerPuntoR.visibility = View.GONE
                                prefManager.setVisibilidadPuntoRevision(false)
                            }
                            //                    ----- Otros ------
                            9->{
                                binding.spinnerTipo.setSelection(p2)
                                prefManager.setTipoRescate(p2)
                                prefManager.setVisibilidadPuntoRevision(false)
                                dataRescateP = TipoRescate()
                                dataRescateP.otro = true
                                showPopUp1(p0!!.getItemAtPosition(p2).toString())
                                binding.spinnerPuntoR.visibility = View.VISIBLE
                                prefManager.setVisibilidadPuntoRevision(true)
                                dataActivityViewM.buscarOtros()
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {  }
        }

        nacionalidadesAdapter = IsoAdapter( emptyList()) { datos, pos ->
            val paises = dataActivityViewM.paises.value
            val iso3 = dataActivityViewM.iso3.value

            val indexIso = iso3?.indexOf(datos.iso3)
            val nacionalidad = paises!![indexIso!!]

//            Toast.makeText(requireContext(), nacionalidad, Toast.LENGTH_LONG).show()
//            positionRegistro = pos
            navigateToNacionalidad(nacionalidad)
        }
        binding.RecNacionalidades.adapter = nacionalidadesAdapter
        dataActivityViewM.datosIso.observe(viewLifecycleOwner){
            nacionalidadesAdapter.registroByIso = it
            nacionalidadesAdapter.notifyDataSetChanged()
        }

        familiaAdapter = FamiliaAdapter(emptyList()) { datos, pos ->
//            Toast.makeText(requireContext(), datos.toString(), Toast.LENGTH_LONG).show()
            navigateToFamiliaA(datos)
        }
        binding.RecFamilias.adapter = familiaAdapter
        dataActivityViewM.numerosFamilias.observe(viewLifecycleOwner){
            familiaAdapter.registroByFamilia = it
            familiaAdapter.notifyDataSetChanged()
        }


        binding.editTextHora.setOnClickListener { ShowTimePickerDialog() }

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy")
        val current = LocalDateTime.now().format(formatter)
        binding.textViewFecha.text = "$current"

        binding.btnEnviar.setOnClickListener {
//            Toast.makeText(requireContext(), "ENVIAR", Toast.LENGTH_LONG).show()
            dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            verifyData()
            showPopUpEnviar()
        }


        binding.btnNcionalidad.setOnClickListener {
//            ----------------------
//            ----------------------
//            verificar Datos del Punto
            dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            verifyData()

            val intentRegistroNombres = Intent(requireContext(), RescateNombresActivity::class.java)
            startActivity(intentRegistroNombres)

        }

        binding.btnFamilias.setOnClickListener {
            //            verificar Datos del Punto
            dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            verifyData()

            val intentRegistroFamilias = Intent(requireContext(), RescateFamiliasActivity::class.java)
            intentRegistroFamilias.putExtra( RescateFamiliasActivity.EXTRA_NOM_FAMILIA, dataActivityViewM.numFamilia.value)
            startActivity(intentRegistroFamilias)
        }

        dataActivityViewM.pasarVentana.observe(viewLifecycleOwner){
            prefManager.setTipoRescate(0)
            prefManager.setPuntoRevision("")
            prefManager.setVisibilidadPuntoRevision(false)
            binding.editTextHora.setText("")
//            if (it){
//                navigateToMensajes()
//            }
        }

        if(prefManager.isVisiblePuntoRevision()!!){
            binding.spinnerPuntoR.visibility = View.VISIBLE
        } else{
            binding.spinnerPuntoR.visibility = View.GONE
        }

        dataActivityViewM.onCreate()

        return binding.root
    }

    private fun verifyData() {

        dataActivityViewM.datosBRescate.value = Rescate(
            (binding.textViewOR.text.toString()).replace("OR: ", ""),
            (binding.textViewFecha.text.toString()).replace("Fecha: ", "") ,
            binding.editTextHora.text.toString(),
            dataRescateP)
    }

    private fun navigateToNacionalidad(nacionalidad : String) {
        val intentRegistroNombres = Intent(requireContext(), RescateNombresActivity::class.java)
        intentRegistroNombres.putExtra(RescateNombresActivity.EXTRA_NACIONALIDAD, nacionalidad)
        startActivity(intentRegistroNombres)
    }

    private fun navigateToFamiliaA(datos: Int) {
        val intentRegistroFamilias = Intent(requireContext(), RescateFamiliasActivity::class.java)
        intentRegistroFamilias.putExtra( RescateFamiliasActivity.EXTRA_NOM_FAMILIA, datos)
        startActivity(intentRegistroFamilias)
    }


    private fun ShowTimePickerDialog() {
        val timePicker = TimePickerFragment { time -> onTimeSelected(time)}
        timePicker.show(childFragmentManager,"time")
    }

    private fun onTimeSelected(time: String){
        binding.editTextHora.setText(time)
        binding.editTextHora.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        prefManager.setVisibilidadPuntoRevision(false)
    }

    private fun showPopUp1(tipo : String){

        var salir = false
        var bindings : ActivityPopupCarreteroBinding
        bindings = ActivityPopupCarreteroBinding.inflate(layoutInflater)

        var dialog = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(bindings.root)

//        Spinner seccion
        val adapterSpinnerC = ArrayAdapter.createFromResource(
            requireActivity().applicationContext, R.array.tipo_vehiculo, R.layout.spinner_item)
        adapterSpinnerC.setDropDownViewResource(R.layout.simple_spinner_item)

        val adapterSpinnerF = ArrayAdapter.createFromResource(
            requireActivity().applicationContext, R.array.tipo_ferrocarril, R.layout.spinner_item)
        adapterSpinnerC.setDropDownViewResource(R.layout.simple_spinner_item1)

        bindings.spinnerTipoVehiculo.adapter = adapterSpinnerC
        bindings.spinnerTipoFerro.adapter = adapterSpinnerF


        val spinnerMun = ArrayAdapter(requireActivity().applicationContext, android.R.layout.simple_spinner_item, municipiosNom)
        bindings.spinnerMuncipio.threshold = 1
        bindings.spinnerMuncipio.setAdapter(spinnerMun)

        bindings.spinnerMuncipio.setOnItemClickListener { adapterView, view, i, l ->
            hideKeyboard()
        }

        when(tipo){
            "CARRETERO" -> {
                bindings.LLCarretero.visibility = View.VISIBLE
                bindings.LLMunicipio.visibility = View.VISIBLE
                bindings.LLPresuntosD.visibility = View.VISIBLE

            }
            "CASA DE SEGURIDAD" -> {
                bindings.LLMunicipio.visibility = View.VISIBLE
                bindings.LLPresuntosD.visibility = View.VISIBLE

            }

            "FERROCARRIL" -> {
                bindings.LLFerrocarril.visibility = View.VISIBLE
                bindings.LLPresuntosD.visibility = View.VISIBLE

            }
            "HOTEL" -> {
                bindings.LLHotel.visibility = View.VISIBLE
                bindings.LLMunicipio.visibility = View.VISIBLE
                bindings.LLPresuntosD.visibility = View.VISIBLE

            }
            "PUESTOS A DISPOSICIÓN" -> {
                bindings.LLPuestosaD.visibility = View.VISIBLE
                bindings.LLPresuntosD.visibility = View.VISIBLE

            }
            "OTRO" -> {
                bindings.LLMunicipio.visibility = View.VISIBLE
                bindings.LLPresuntosD.visibility = View.VISIBLE
            }
            else -> {

            }
        }

        bindings.checkboxJuez.isChecked = true

        bindings.checkboxJuez.setOnClickListener {
            bindings.checkboxJuez.isChecked = true
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxReclusorio.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = true
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxPoliciaF.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = true
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxDif.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = true
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxPoliciaE.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = true
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxPoliciaM.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = true
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxGN.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = true
            bindings.checkboxFiscalia.isChecked = false
        }

        bindings.checkboxFiscalia.setOnClickListener {
            bindings.checkboxJuez.isChecked = false
            bindings.checkboxReclusorio.isChecked = false
            bindings.checkboxPoliciaF.isChecked = false
            bindings.checkboxDif.isChecked = false
            bindings.checkboxPoliciaE.isChecked = false
            bindings.checkboxPoliciaM.isChecked = false
            bindings.checkboxGN.isChecked = false
            bindings.checkboxFiscalia.isChecked = true
        }

        bindings.checkPuestos.setOnClickListener {

            val activo =  bindings.checkPuestos.isChecked
//            bindings.checkPuestos.isChecked = !activo
            if(activo){
                bindings.etNumeroPuestos.visibility = View.VISIBLE
            } else {
                bindings.etNumeroPuestos.visibility = View.GONE
            }
        }

        bindings.editTextTipo1.setText(tipo)

        bindings.closeImg.setOnClickListener {

            when(tipo){
                "CARRETERO" -> {
                    val tipoV = bindings.spinnerTipoVehiculo.selectedItem.toString()
                    val lineaA = bindings.etLineaAutobus.text.toString()
                    val noEconom = bindings.etEconomico.text.toString()
                    val placasC = bindings.etPlacas.text.toString()
                    val vehAse = bindings.checkVehiculo.isChecked
                    val munic = bindings.spinnerMuncipio.text.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    when(bindings.spinnerTipoVehiculo.selectedItemPosition){

                        0 -> {
                            bindings.spinnerTipoVehiculoE.visibility = View.VISIBLE
                            showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                        }
                        1 ->{
                            bindings.spinnerTipoVehiculoE.visibility = View.GONE

                            if (lineaA.isNullOrEmpty()){
                                bindings.etLineaAutobus.setError("LLENAR PARA CONTINUAR", icon)
                                bindings.etLineaAutobus.requestFocus()
//                                showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                            } else{
                                bindings.etLineaAutobus.error = null
                                if (noEconom.isNullOrEmpty()){
                                    bindings.etEconomico.setError("LLENAR PARA CONTINUAR", icon)
                                    bindings.etEconomico.requestFocus()
                                }else{
                                    bindings.etEconomico.error = null
                                    if (placasC.isNullOrEmpty()){
                                        bindings.etPlacas.setError("LLENAR PARA CONTINUAR", icon)
                                        bindings.etPlacas.requestFocus()
                                    } else{
                                        bindings.etPlacas.error = null
                                        if (munic.isNullOrEmpty()){
                                            bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                                            bindings.spinnerMuncipio.requestFocus()
                                        } else {
                                            bindings.etPlacas.error = null

                                            if (Pdelincuentes and (numPDelinc == 0) ){
                                                bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                                bindings.etNumeroPuestos.requestFocus()
                                            } else{
                                                bindings.etNumeroPuestos.error = null

                                                dataRescateP.tipoVehic = tipoV
                                                dataRescateP.lineaAutobus = lineaA
                                                dataRescateP.numeroEcono = noEconom
                                                dataRescateP.placas = placasC
                                                dataRescateP.vehiculoAseg = vehAse
                                                dataRescateP.municipio = munic
                                                dataRescateP.presuntosDelincuentes = Pdelincuentes
                                                dataRescateP.numPresuntosDelincuentes = numPDelinc

                                                salir = true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        2,4,5,6 ->{
                            bindings.spinnerTipoVehiculoE.visibility = View.GONE

                            if (placasC.isNullOrEmpty()){
                                bindings.etPlacas.setError("LLENAR PARA CONTINUAR", icon)
                                bindings.etPlacas.requestFocus()
                            } else{
                                bindings.etPlacas.error = null
                                if (munic.isNullOrEmpty()){
                                    bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                                    bindings.spinnerMuncipio.requestFocus()
                                } else {
                                    bindings.etPlacas.error = null

                                    if (Pdelincuentes and (numPDelinc == 0) ){
                                        bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                        bindings.etNumeroPuestos.requestFocus()
                                    } else{
                                        bindings.etNumeroPuestos.error = null
                                        salir = true
                                    }
                                }
                            }
                        }
                        3 ->{
                            bindings.spinnerTipoVehiculoE.visibility = View.GONE

                            if (noEconom.isNullOrEmpty()){
                                bindings.etEconomico.setError("LLENAR PARA CONTINUAR", icon)
                                bindings.etEconomico.requestFocus()
                            }else{
                                bindings.etEconomico.error = null
                                if (placasC.isNullOrEmpty()){
                                    bindings.etPlacas.setError("LLENAR PARA CONTINUAR", icon)
                                    bindings.etPlacas.requestFocus()
                                } else{
                                    bindings.etPlacas.error = null
                                    if (munic.isNullOrEmpty()){
                                        bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                                        bindings.spinnerMuncipio.requestFocus()
                                    } else {
                                        bindings.etPlacas.error = null

                                        if (Pdelincuentes and (numPDelinc == 0) ){
                                            bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                            bindings.etNumeroPuestos.requestFocus()
                                        } else{
                                            bindings.etNumeroPuestos.error = null
                                            salir = true
                                        }
                                    }
                                }
                            }

                        }
                        else -> {

                        }

                    }

                }
                "CASA DE SEGURIDAD" -> {
                    val munic = bindings.spinnerMuncipio.text.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (munic.isNullOrEmpty()) {
                        bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                        bindings.spinnerMuncipio.requestFocus()
                    } else {
                        bindings.etPlacas.error = null

                        if (Pdelincuentes and (numPDelinc == 0)) {
                            bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                            bindings.etNumeroPuestos.requestFocus()
                        } else {
                            bindings.etNumeroPuestos.error = null

                            dataRescateP.municipio = munic
                            dataRescateP.presuntosDelincuentes = Pdelincuentes
                            dataRescateP.numPresuntosDelincuentes = numPDelinc

                            salir = true
                        }

                    }
                }

                "FERROCARRIL" -> {

                    val empresaC = bindings.spinnerTipoFerro.selectedItem.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }


                    if (empresaC.isNullOrEmpty()){
                        bindings.spinnerTipoFerroE.visibility = View.VISIBLE
                        showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                    } else{
                        bindings.spinnerTipoFerroE.visibility = View.GONE
                        if (Pdelincuentes and (numPDelinc == 0) ){
                            bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                            bindings.etNumeroPuestos.requestFocus()
                        } else{
                            bindings.etNumeroPuestos.error = null

                            dataRescateP.empresa = empresaC
                            dataRescateP.presuntosDelincuentes = Pdelincuentes
                            dataRescateP.numPresuntosDelincuentes = numPDelinc

                            salir = true
                        }

                    }



                }
                "HOTEL" -> {
                    val nombreH = bindings.etNombreHotel.text.toString()
                    val munic = bindings.spinnerMuncipio.text.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (nombreH.isNullOrEmpty()) {
                        bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                        bindings.spinnerMuncipio.requestFocus()

                    }else{
                        bindings.spinnerMuncipio.error = null
                        if (munic.isNullOrEmpty()) {
                            bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                            bindings.spinnerMuncipio.requestFocus()
                        } else {
                            bindings.etPlacas.error = null

                            if (Pdelincuentes and (numPDelinc == 0)) {
                                bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                bindings.etNumeroPuestos.requestFocus()
                            } else {
                                bindings.etNumeroPuestos.error = null

                                dataRescateP.nombreHotel = nombreH
                                dataRescateP.municipio = munic
                                dataRescateP.presuntosDelincuentes = Pdelincuentes
                                dataRescateP.numPresuntosDelincuentes = numPDelinc

                                salir = true
                            }

                        }
                    }




                }
                "PUESTOS A DISPOSICIÓN" -> {

                    dataRescateP.juezCalif = bindings.checkboxJuez.isChecked
                    dataRescateP.reclusorio = bindings.checkboxReclusorio.isChecked
                    dataRescateP.policiaFede = bindings.checkboxPoliciaF.isChecked
                    dataRescateP.dif = bindings.checkboxDif.isChecked
                    dataRescateP.policiaEsta = bindings.checkboxPoliciaE.isChecked
                    dataRescateP.policiaMuni = bindings.checkboxPoliciaM.isChecked
                    dataRescateP.guardiaNaci = bindings.checkboxGN.isChecked
                    dataRescateP.fiscalia = bindings.checkboxFiscalia.isChecked
                    dataRescateP.otrasAuto = bindings.checkboxOtras.isChecked

                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (Pdelincuentes and (numPDelinc == 0)) {
                        bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                        bindings.etNumeroPuestos.requestFocus()
                    } else {
                        bindings.etNumeroPuestos.error = null

                        dataRescateP.presuntosDelincuentes = Pdelincuentes
                        dataRescateP.numPresuntosDelincuentes = numPDelinc

                        salir = true
                    }



                }
                "OTRO" -> {

                    val munic = bindings.spinnerMuncipio.text.toString()
                    dataRescateP.municipio = munic

                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (Pdelincuentes and (numPDelinc == 0)) {
                        bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                        bindings.etNumeroPuestos.requestFocus()
                    } else {
                        bindings.etNumeroPuestos.error = null

                        dataRescateP.presuntosDelincuentes = Pdelincuentes
                        dataRescateP.numPresuntosDelincuentes = numPDelinc

                        salir = true
                    }
                }
                else -> {

                }
            }

            if (salir){
                bindings.LLCarretero.visibility = View.GONE
                bindings.LLHotel.visibility = View.GONE
                bindings.LLFerrocarril.visibility = View.GONE
                bindings.LLMunicipio.visibility = View.GONE
                bindings.LLPresuntosD.visibility = View.GONE
                bindings.LLPuestosaD.visibility = View.GONE

                dialog.dismiss()
            }
        }

        bindings.btnGuardar.setOnClickListener {
            when(tipo){
                "CARRETERO" -> {
                    val tipoV = bindings.spinnerTipoVehiculo.selectedItem.toString()
                    val lineaA = bindings.etLineaAutobus.text.toString()
                    val noEconom = bindings.etEconomico.text.toString()
                    val placasC = bindings.etPlacas.text.toString()
                    val vehAse = bindings.checkVehiculo.isChecked
                    val munic = bindings.spinnerMuncipio.text.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    when(bindings.spinnerTipoVehiculo.selectedItemPosition){

                        0 -> {
                            bindings.spinnerTipoVehiculoE.visibility = View.VISIBLE
                            showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                        }
                        1 ->{
                            bindings.spinnerTipoVehiculoE.visibility = View.GONE

                            if (lineaA.isNullOrEmpty()){
                                bindings.etLineaAutobus.setError("LLENAR PARA CONTINUAR", icon)
                                bindings.etLineaAutobus.requestFocus()
//                                showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                            } else{
                                bindings.etLineaAutobus.error = null
                                if (noEconom.isNullOrEmpty()){
                                    bindings.etEconomico.setError("LLENAR PARA CONTINUAR", icon)
                                    bindings.etEconomico.requestFocus()
                                }else{
                                    bindings.etEconomico.error = null
                                    if (placasC.isNullOrEmpty()){
                                        bindings.etPlacas.setError("LLENAR PARA CONTINUAR", icon)
                                        bindings.etPlacas.requestFocus()
                                    } else{
                                        bindings.etPlacas.error = null
                                        if (munic.isNullOrEmpty()){
                                            bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                                            bindings.spinnerMuncipio.requestFocus()
                                        } else {
                                            bindings.etPlacas.error = null

                                            if (Pdelincuentes and (numPDelinc == 0) ){
                                                bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                                bindings.etNumeroPuestos.requestFocus()
                                            } else{
                                                bindings.etNumeroPuestos.error = null

                                                dataRescateP.tipoVehic = tipoV
                                                dataRescateP.lineaAutobus = lineaA
                                                dataRescateP.numeroEcono = noEconom
                                                dataRescateP.placas = placasC
                                                dataRescateP.vehiculoAseg = vehAse
                                                dataRescateP.municipio = munic
                                                dataRescateP.presuntosDelincuentes = Pdelincuentes
                                                dataRescateP.numPresuntosDelincuentes = numPDelinc

                                                salir = true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        2,4,5,6 ->{
                            bindings.spinnerTipoVehiculoE.visibility = View.GONE

                            if (placasC.isNullOrEmpty()){
                                bindings.etPlacas.setError("LLENAR PARA CONTINUAR", icon)
                                bindings.etPlacas.requestFocus()
                            } else{
                                bindings.etPlacas.error = null
                                if (munic.isNullOrEmpty()){
                                    bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                                    bindings.spinnerMuncipio.requestFocus()
                                } else {
                                    bindings.etPlacas.error = null

                                    if (Pdelincuentes and (numPDelinc == 0) ){
                                        bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                        bindings.etNumeroPuestos.requestFocus()
                                    } else{
                                        bindings.etNumeroPuestos.error = null
                                        salir = true
                                    }
                                }
                            }
                        }
                        3 ->{
                            bindings.spinnerTipoVehiculoE.visibility = View.GONE

                            if (noEconom.isNullOrEmpty()){
                                bindings.etEconomico.setError("LLENAR PARA CONTINUAR", icon)
                                bindings.etEconomico.requestFocus()
                            }else{
                                bindings.etEconomico.error = null
                                if (placasC.isNullOrEmpty()){
                                    bindings.etPlacas.setError("LLENAR PARA CONTINUAR", icon)
                                    bindings.etPlacas.requestFocus()
                                } else{
                                    bindings.etPlacas.error = null
                                    if (munic.isNullOrEmpty()){
                                        bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                                        bindings.spinnerMuncipio.requestFocus()
                                    } else {
                                        bindings.etPlacas.error = null

                                        if (Pdelincuentes and (numPDelinc == 0) ){
                                            bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                            bindings.etNumeroPuestos.requestFocus()
                                        } else{
                                            bindings.etNumeroPuestos.error = null
                                            salir = true
                                        }
                                    }
                                }
                            }

                        }
                        else -> {

                        }

                    }

                }
                "CASA DE SEGURIDAD" -> {
                    val munic = bindings.spinnerMuncipio.text.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (munic.isNullOrEmpty()) {
                        bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                        bindings.spinnerMuncipio.requestFocus()
                    } else {
                        bindings.etPlacas.error = null

                        if (Pdelincuentes and (numPDelinc == 0)) {
                            bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                            bindings.etNumeroPuestos.requestFocus()
                        } else {
                            bindings.etNumeroPuestos.error = null

                            dataRescateP.municipio = munic
                            dataRescateP.presuntosDelincuentes = Pdelincuentes
                            dataRescateP.numPresuntosDelincuentes = numPDelinc

                            salir = true
                        }

                    }
                }

                "FERROCARRIL" -> {

                    val empresaC = bindings.spinnerTipoFerro.selectedItem.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }


                    if (empresaC.isNullOrEmpty()){
                        bindings.spinnerTipoFerroE.visibility = View.VISIBLE
                        showToastError("LLENAR PARA CONTINUAR", Toast.LENGTH_LONG)
                    } else{
                        bindings.spinnerTipoFerroE.visibility = View.GONE
                        if (Pdelincuentes and (numPDelinc == 0) ){
                            bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                            bindings.etNumeroPuestos.requestFocus()
                        } else{
                            bindings.etNumeroPuestos.error = null

                            dataRescateP.empresa = empresaC
                            dataRescateP.presuntosDelincuentes = Pdelincuentes
                            dataRescateP.numPresuntosDelincuentes = numPDelinc

                            salir = true
                        }

                    }



                }
                "HOTEL" -> {
                    val nombreH = bindings.etNombreHotel.text.toString()
                    val munic = bindings.spinnerMuncipio.text.toString()
                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (nombreH.isNullOrEmpty()) {
                        bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                        bindings.spinnerMuncipio.requestFocus()

                    }else{
                        bindings.spinnerMuncipio.error = null
                        if (munic.isNullOrEmpty()) {
                            bindings.spinnerMuncipio.setError("LLENAR PARA CONTINUAR", icon)
                            bindings.spinnerMuncipio.requestFocus()
                        } else {
                            bindings.etPlacas.error = null

                            if (Pdelincuentes and (numPDelinc == 0)) {
                                bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                                bindings.etNumeroPuestos.requestFocus()
                            } else {
                                bindings.etNumeroPuestos.error = null

                                dataRescateP.nombreHotel = nombreH
                                dataRescateP.municipio = munic
                                dataRescateP.presuntosDelincuentes = Pdelincuentes
                                dataRescateP.numPresuntosDelincuentes = numPDelinc

                                salir = true
                            }

                        }
                    }




                }
                "PUESTOS A DISPOSICIÓN" -> {

                    dataRescateP.juezCalif = bindings.checkboxJuez.isChecked
                    dataRescateP.reclusorio = bindings.checkboxReclusorio.isChecked
                    dataRescateP.policiaFede = bindings.checkboxPoliciaF.isChecked
                    dataRescateP.dif = bindings.checkboxDif.isChecked
                    dataRescateP.policiaEsta = bindings.checkboxPoliciaE.isChecked
                    dataRescateP.policiaMuni = bindings.checkboxPoliciaM.isChecked
                    dataRescateP.guardiaNaci = bindings.checkboxGN.isChecked
                    dataRescateP.fiscalia = bindings.checkboxFiscalia.isChecked
                    dataRescateP.otrasAuto = bindings.checkboxOtras.isChecked

                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (Pdelincuentes and (numPDelinc == 0)) {
                        bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                        bindings.etNumeroPuestos.requestFocus()
                    } else {
                        bindings.etNumeroPuestos.error = null

                        dataRescateP.presuntosDelincuentes = Pdelincuentes
                        dataRescateP.numPresuntosDelincuentes = numPDelinc

                        salir = true
                    }



                }
                "OTRO" -> {

                    val munic = bindings.spinnerMuncipio.text.toString()
                    dataRescateP.municipio = munic

                    val Pdelincuentes = bindings.checkPuestos.isChecked
                    var numPDelinc = 0
                    if (Pdelincuentes and !bindings.etNumeroPuestos.text.toString().isNullOrEmpty()){
                        numPDelinc = bindings.etNumeroPuestos.text.toString().toInt()
                    }

                    if (Pdelincuentes and (numPDelinc == 0)) {
                        bindings.etNumeroPuestos.setError("LLENAR CON MAYOR A 0", icon)
                        bindings.etNumeroPuestos.requestFocus()
                    } else {
                        bindings.etNumeroPuestos.error = null

                        dataRescateP.presuntosDelincuentes = Pdelincuentes
                        dataRescateP.numPresuntosDelincuentes = numPDelinc

                        salir = true
                    }
                }
                else -> {

                }
            }

            if (salir){
                bindings.LLCarretero.visibility = View.GONE
                bindings.LLHotel.visibility = View.GONE
                bindings.LLFerrocarril.visibility = View.GONE
                bindings.LLMunicipio.visibility = View.GONE
                bindings.LLPresuntosD.visibility = View.GONE
                bindings.LLPuestosaD.visibility = View.GONE

                dialog.dismiss()
            }
        }

        dialog.show()

    }

    private fun showPopUpEnviar(){

        var bindings1 : ActivityPopupEnviarBinding
        bindings1 = ActivityPopupEnviarBinding.inflate(layoutInflater)

        var dialog3 = Dialog(requireContext())
        dialog3.setCancelable(false)
        dialog3.setContentView(bindings1.root)

        bindings1.btnEnviar.setOnClickListener {
            if(isConnected){
//              Si esta connectado se envia la informacion del rescate al servidor
                try {
                    dataActivityViewM.GuardarRescateFAPI()
                } catch (e : Exception){
//                  En dado caso que aunque tenga internet y no pueda enviar datos
//                  Se guarda la informacion en la tabla para envio despues
                    dataActivityViewM.GuardarRescateDB()

//                  Se eliminan de cache los datos almacenados
                    object : CountDownTimer(1000, 100){
                        override fun onTick(p0: Long) {
                            bindings1.pbEnvirarConteoPopUp.visibility = View.VISIBLE
                        }

                        override fun onFinish() {
                            bindings1.pbEnvirarConteoPopUp.visibility = View.GONE

//                      Se settean los valores en Cero de los datos guardados
                            binding.spinnerTipo.setSelection(prefManager.getTipoRescate()!!,false)
                            binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                            binding.LLPuntoRescate.visibility = View.GONE
                            dataActivityViewM.numerosFamilias.value = emptyList()
                            dataActivityViewM.datosIso.value = emptyList()
                            dataActivityViewM.delAllDatos()
                            prefManager.setPuntoRevision("")
                            prefManager.setVisibilidadPuntoRevision(false)
                            prefManager.setTipoRescate(0)

//                       SE LLama a la funcion de pasar de vista de fragmento
                            dataActivityViewM.pasarVentana.value = true

                            Log.e("Error al enviar Nombres y Familias", e.toString())
//                        Y Aparecera el mensaje de antes
//                            popUpInternet()
                            dialog3.dismiss()
                        }
                    }.start()

                }

//              Se crea el Pin de mensaje y se guarda en mensajes
                dataActivityViewM.createPin()
//              Se eliminan de cache los datos almacenados
                object : CountDownTimer(1000, 100){
                    override fun onTick(p0: Long) {
                        bindings1.pbEnvirarConteoPopUp.visibility = View.VISIBLE
                    }

                    override fun onFinish() {
                        bindings1.pbEnvirarConteoPopUp.visibility = View.GONE
//                      Se settean los valores en Cero de los datos guardados
                        binding.spinnerTipo.setSelection(prefManager.getTipoRescate()!!,false)
                        binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                        binding.LLPuntoRescate.visibility = View.GONE
                        dataActivityViewM.numerosFamilias.value = emptyList()
                        dataActivityViewM.datosIso.value = emptyList()
                        dataActivityViewM.delAllDatos()
                        prefManager.setPuntoRevision("")
                        prefManager.setVisibilidadPuntoRevision(false)
                        prefManager.setTipoRescate(0)
//                      SE LLama a la funcion de pasar de vista de fragmento
                        dataActivityViewM.pasarVentana.value = true
                        dialog3.dismiss()
                    }

                }.start()

//          Sin Internet
            } else {
//              Se guarda la informacion en la tabla para envio despues
                dataActivityViewM.GuardarRescateDB()

//              Se crea el Pin de mensaje y se guarda en mensajes
                dataActivityViewM.createPin()

//              Se eliminan de cache los datos almacenados
                object : CountDownTimer(1000, 100){
                    override fun onTick(p0: Long) {
                        bindings1.pbEnvirarConteoPopUp.visibility = View.VISIBLE
                    }

                    override fun onFinish() {
                        bindings1.pbEnvirarConteoPopUp.visibility = View.GONE

//                      Se settean los valores en Cero de los datos guardados
                        binding.spinnerTipo.setSelection(prefManager.getTipoRescate()!!,false)
                        binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                        binding.LLPuntoRescate.visibility = View.GONE
                        dataActivityViewM.numerosFamilias.value = emptyList()
                        dataActivityViewM.datosIso.value = emptyList()
                        dataActivityViewM.delAllDatos()
                        prefManager.setPuntoRevision("")
                        prefManager.setVisibilidadPuntoRevision(false)
                        prefManager.setTipoRescate(0)

//                      SE LLama a la funcion de pasar de vista de fragmento
                        dataActivityViewM.pasarVentana.value = true
                        dialog3.dismiss()
                    }
                }.start()
            }
        }

        bindings1.btnRevisar.setOnClickListener {
            dialog3.dismiss()
        }
        dialog3.show()
    }

    fun vaciarDatos(pbPopUp : ProgressBar){
        object : CountDownTimer(1000, 100){
            override fun onTick(p0: Long) {
                pbPopUp.visibility = View.VISIBLE
            }

            override fun onFinish() {
                pbPopUp.visibility = View.GONE
//              Se settean los valores en Cero de los datos guardados
                binding.spinnerTipo.setSelection(prefManager.getTipoRescate()!!,false)
                binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                binding.LLPuntoRescate.visibility = View.GONE
                dataActivityViewM.numerosFamilias.value = emptyList()
                dataActivityViewM.datosIso.value = emptyList()
                dataActivityViewM.delAllDatos()
            }

        }.start()
    }

    private fun popUpInternet() {
        var dialog1 = Dialog(requireContext())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.setCancelable(false)
        dialog1.setContentView(R.layout.activity_popup_internet)

        val btn_cerrarD = dialog1.findViewById<Button>(R.id.btnOK)

        btn_cerrarD.setOnClickListener {
            dialog1.dismiss()
        }

        dialog1.show()
    }

    override fun onResume() {
        super.onResume()
        dataActivityViewM.onCreate()
        binding.spinnerTipo.setSelection(prefManager.getTipoRescate()!!,false)
        if(prefManager.isVisiblePuntoRevision()!!){
            binding.spinnerPuntoR.visibility = View.VISIBLE
        } else{
            binding.spinnerPuntoR.visibility = View.GONE
        }
    }

    fun showToastError(texto : String, duracion : Int){

        val displaySize = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displaySize)

        val bindingToast = ToastLayoutErrorBinding.inflate(layoutInflater)
        bindingToast.textview.setText(texto)
        val toast = Toast(requireContext())
        toast.view = bindingToast.root
        toast.duration = duracion
        toast.setGravity(Gravity.BOTTOM, 0, (displaySize.heightPixels / 2) - 20)
//        displaySize.heightPixels
        toast.show()

    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun navigateToMensajes() {
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notifications)
//        (activity as? MainActivity)?.navigateToNavBarDestination(R.id.navigation_notifications)
    }
}

