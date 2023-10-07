package com.example.electrorui.ui.fragments


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.electrorui.R
import com.example.electrorui.databinding.ActivityPopupCarreteroBinding
import com.example.electrorui.databinding.ActivityPopupEnviarBinding
import com.example.electrorui.databinding.FragmentCapturaBinding
import com.example.electrorui.databinding.ToastLayoutErrorBinding
import com.example.electrorui.db.PrefManager
import com.example.electrorui.ui.ConteoRActivity
import com.example.electrorui.ui.RescateFamiliasActivity
import com.example.electrorui.ui.RescateNombresActivity
import com.example.electrorui.ui.adapters.FamiliaAdapter
import com.example.electrorui.ui.adapters.IsoAdapter
import com.example.electrorui.ui.viewModel.Captura_FVM
import com.example.electrorui.usecase.model.Rescate
import com.example.electrorui.usecase.model.TipoRescate
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener
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

//     --------------------------------------------------------------------------
//  --------------------------------------------------------------------------------
//                       Sección de inicialización de Datos
//  --------------------------------------------------------------------------------
//     --------------------------------------------------------------------------

//  ##  Inicializar Icono para Alertas del Toast

        val colorIcon = R.color.rojo

        icon = AppCompatResources.getDrawable(requireContext(),R.drawable.ic_error_24)!!
        val icon1 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_air_24)!!
        val icon2 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_road)!!
        val icon3 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_casa_24)!!
        val icon4 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_bus_24)!!
        val icon5 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_tren_24)!!
        val icon6 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_hotel)!!
        val icon7 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_police_24)!!
        val icon8 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_voluntarios_24)!!
        val icon9 = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_otro_24)!!

        DrawableCompat.setTint(icon, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon1, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon2, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon3, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon4, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon5, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon6, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon7, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon8, resources.getColor(colorIcon))
        DrawableCompat.setTint(icon9, resources.getColor(colorIcon))

        icon.setBounds(0, 0, icon.intrinsicWidth, icon.intrinsicHeight)
//############## inicializa el estado de conexión a INTERNET ###############
//      Verificar si al momento de abrir esta ventana, hay internet
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true
//      Se guarda la info de que el dispositivo tiene internet
        prefManager.setConnection(isConnected)
        vistoMensajeInternet = prefManager.vistoPopUInternet()!!
        if (!isConnected and !vistoMensajeInternet){
//          Dialogo para la alerta de ingresos sin internet
            popUpInternet()
            prefManager.setvistasPopUpInternet(true)
        }

//############## inicializar spinner de Selección de Tipo de Punto --Vacio-- ###############
//        var dataTipoPunto = emptyList<String>()
//        val adapterSpinner = ArrayAdapter(
//            requireActivity().applicationContext,
//            android.R.layout.simple_spinner_item,
//            dataTipoPunto
//        )
//        adapterSpinner.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1)
//        binding.spinnerTipo.setAdapter(adapterSpinner)

        binding.spinnerTipo.apply {
            setSpinnerAdapter(IconSpinnerAdapter(this))
            setItems(
                arrayListOf(
                    IconSpinnerItem(text = "AEROPUERTO", icon= icon1),
                    IconSpinnerItem(text = "CARRETERO", icon= icon2),
                    IconSpinnerItem(text = "CASA DE SEGURIDAD", icon= icon3),
                    IconSpinnerItem(text = "CENTRAL DE AUTOBUSES", icon= icon4),
                    IconSpinnerItem(text = "FERROCARRIL", icon= icon5),
                    IconSpinnerItem(text = "HOTEL", icon= icon6),
                    IconSpinnerItem(text = "PUESTOS A DISPOSICIÓN", icon= icon7),
                    IconSpinnerItem(text = "VOLUNTARIOS", icon= icon8),
                    IconSpinnerItem(text = "OTRO", icon=icon9)))
            getSpinnerRecyclerView().layoutManager = GridLayoutManager(context, 1)
            selectItemByIndex(0) // select a default item.
            lifecycleOwner = viewLifecycleOwner
        }

//############## inicializar spinner de Selección de Punto de Rescate --Vacio-- ###############
        var dataTipoRescate = emptyList<String>()
        val spinnerTipoRadapter = ArrayAdapter(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            dataTipoRescate
        )
        binding.spinnerPuntoR.threshold = 1
        binding.spinnerPuntoR.setAdapter(spinnerTipoRadapter)

// --------- Cargar por defecto la información de la fecha y la hora en los editText
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy")
        val current = LocalDateTime.now().format(formatter)
        binding.textViewFecha.text = "$current"

        val formatterH = DateTimeFormatter.ofPattern("HH:mm")
        val currentH = LocalDateTime.now().format(formatterH)
        binding.editTextHora.setText(currentH)

// --------- Cargar por defecto el tipo de Rescate Guardado
        binding.spinnerTipo.selectItemByIndex(prefManager.getTipoRescate()!!)
// --------- Cargar por defecto el nombre del Agente
        binding.tvAgenteM.setText("${(prefManager.getUsername())?.uppercase()}")

// --------- Se actualizan los datos de la OR
        dataActivityViewM.oficinas.observe(viewLifecycleOwner){
            val oficinas = it
            val estado = prefManager.getState()!!.toInt()

            val oficina = oficinas[estado-1]
            binding.textViewOR.setText("OR: ${oficina}")
            dataActivityViewM.dataAditional(oficina, prefManager.getUsername()!!)
        }

// --------- Se actualizan los datos a mostrar del spinner --Punto de Rescate--
        dataActivityViewM.puntoRescateNom.observe(viewLifecycleOwner){
            puntoRescateNom = it
        }
// --------- Se actualiza el dato mostrado en el spinner --Punto de Rescate--
        dataActivityViewM.etPuntoRescate.observe(viewLifecycleOwner){
            binding.spinnerPuntoR.setText(it)
        }

        dataActivityViewM.municipiosNom.observe(viewLifecycleOwner){
            municipiosNom = it
        }
// --------- Se actualiza la visibilida de los masivos
        dataActivityViewM.masivo.observe(viewLifecycleOwner){
            if(it){
                binding.tvRescateMasivo.visibility = View.VISIBLE
            } else {
                binding.tvRescateMasivo.visibility = View.GONE
            }
        }

//---------------Spinner seleccion de Punto de rescate -------------------

        binding.spinnerTipo.setOnSpinnerItemSelectedListener(
                OnSpinnerItemSelectedListener<IconSpinnerItem>() { oldIndex, oldItem, newIndex, newItem ->

                    binding.spinnerPuntoR.setText("")
                    binding.spinnerPuntoR.visibility = View.VISIBLE

                    prefManager.setTipoRescate(newIndex)
                    prefManager.setNomTipoRescate(newItem.text.toString())

                    seleccionSpinerTipoDatos(binding.spinnerTipo.selectedIndex, 0)

                })


//---------------- spinner de Selección de Tipo de Punto de Rescate-------------
        binding.spinnerPuntoR.setOnItemClickListener { adapterView, view, i, l ->
            binding.spinnerTipoIcon.visibility = View.GONE
            hideKeyboard()
            val textoPuntoR = binding.spinnerPuntoR.text.toString()
            dataActivityViewM.etPuntoRescate.value = textoPuntoR
            prefManager.setPuntoRevision(textoPuntoR)
            prefManager.setTipoRescate(binding.spinnerTipo.selectedIndex)
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

//############## inicializar el RecyclerView de Nacionalidades ###############
        nacionalidadesAdapter = IsoAdapter( emptyList()) { datos, pos ->
            val paises = dataActivityViewM.paises.value
            val iso3 = dataActivityViewM.iso3.value

            val indexIso = iso3?.indexOf(datos.iso3)
            val nacionalidad = paises!![indexIso!!]

            navigateToNacionalidad(nacionalidad)
        }
        binding.RecNacionalidades.adapter = nacionalidadesAdapter
        dataActivityViewM.datosIso.observe(viewLifecycleOwner){
            nacionalidadesAdapter.registroByIso = it
            nacionalidadesAdapter.notifyDataSetChanged()
        }
//############## inicializar el RecyclerView de Familias ###############
        familiaAdapter = FamiliaAdapter(emptyList()) { datos, pos ->
            navigateToFamiliaA(datos)
        }
        binding.RecFamilias.adapter = familiaAdapter
        dataActivityViewM.numerosFamilias.observe(viewLifecycleOwner){
            familiaAdapter.registroByFamilia = it
            familiaAdapter.notifyDataSetChanged()
        }


// -------------- Floating Button de Conteo Rapido ----------------
        binding.fbConteoRapido.setOnClickListener {

            val infoPuntoR = binding.spinnerPuntoR.text.toString()

            binding.editTextHora.error = null
            if(infoPuntoR.isNullOrEmpty()){
                binding.spinnerPuntoR.setError("LLENAR PARA CONTINUAR", icon)
                binding.spinnerPuntoR.requestFocus()
            } else {
                binding.spinnerPuntoR.error = null
                verifyData()
                dataActivityViewM.saveTipoRescate()
                startActivity(Intent(requireContext(),ConteoRActivity::class.java))
            }
        }
// -------------- Button Agregar Rescate Nacionalidad ----------------
        binding.btnNcionalidad.setOnClickListener {
//            ----------------------
//            verificar Datos del Punto
            binding.editTextHora.error = null
            if(binding.spinnerTipo.selectedIndex == 0){
                binding.spinnerTipoIcon.visibility = View.VISIBLE
                showToastError("Ingresa el tipo de rescate primero", Toast.LENGTH_LONG)
            } else {
                binding.spinnerTipoIcon.visibility = View.GONE

                dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
                verifyData()

                val intentRegistroNombres = Intent(requireContext(), RescateNombresActivity::class.java)
                startActivity(intentRegistroNombres)
            }
        }

// -------------- Button Agregar Rescate Familias ----------------
        binding.btnFamilias.setOnClickListener {
//------------------------------------
//          verificar Datos del Punto
            binding.editTextHora.error = null
            if(binding.spinnerTipo.selectedIndex == 0){
                binding.spinnerTipoIcon.visibility = View.VISIBLE
                showToastError("Ingresa el tipo de rescate primero", Toast.LENGTH_LONG)
            } else{
                binding.spinnerTipoIcon.visibility = View.GONE

                dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
                verifyData()

                val intentRegistroFamilias = Intent(requireContext(), RescateFamiliasActivity::class.java)
                intentRegistroFamilias.putExtra( RescateFamiliasActivity.EXTRA_NOM_FAMILIA, dataActivityViewM.numFamilia.value)
                startActivity(intentRegistroFamilias)
            }
        }
// -------------- Button de Enviar Información ----------------
        binding.btnEnviar.setOnClickListener {
            binding.editTextHora.error = null
            if(binding.spinnerTipo.selectedIndex == 0){
                binding.spinnerTipoIcon.visibility = View.VISIBLE
                showToastError("Ingresa el tipo de rescate primero", Toast.LENGTH_LONG)
            } else{
                binding.spinnerTipoIcon.visibility = View.GONE

                dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
                verifyData()
                showPopUpEnviar()
                prefManager.setPuntoRevision("")
                prefManager.setTipoRescate(0)

                prefManager.setFecha("")
                prefManager.setHora("")
                prefManager.setNomTipoRescate("")
            }
        }

        dataActivityViewM.pasarVentana.observe(viewLifecycleOwner){
            prefManager.setTipoRescate(0)
            prefManager.setPuntoRevision("")
            binding.editTextHora.setText("")
//            if (it){
//                navigateToMensajes()
//            }
        }

        dataActivityViewM.onCreate()

        return binding.root
    }

    private fun verifyData() {

        val oficinaR = (binding.textViewOR.text.toString()).replace("OR: ", "")
        val fecha = (binding.textViewFecha.text.toString()).replace("Fecha: ", "")
        val hora = binding.editTextHora.text.toString()

//        Toast.makeText(requireContext(), hora, Toast.LENGTH_LONG).show()
        seleccionSpinerTipoDatos(binding.spinnerTipo.selectedIndex, 1)

        dataActivityViewM.datosBRescate.value = Rescate(
            oficinaR,
            fecha,
            hora,
            prefManager.getUsername()!!,
            dataRescateP
        )

        prefManager.setOR(oficinaR)
        prefManager.setFecha(fecha)
        prefManager.setHora(hora)
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

    fun seleccionSpinerTipoDatos(num : Int, tipo : Int){
        when(num){
//            aeropuerto
            0 -> {
                dataRescateP = TipoRescate()
                dataRescateP.aeropuerto = true
                if(tipo == 0) dataActivityViewM.buscarAeropuertos()
                if(tipo == 1) dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            }
//            carretero
            1 -> {
                dataRescateP = TipoRescate()
                dataRescateP.carretero = true
                if(tipo == 0) dataActivityViewM.buscarCarretero()
                if(tipo == 1) dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            }
//            casa de seguridad
            2 -> {
                dataRescateP = TipoRescate()
                dataRescateP.casaSeguridad = true
                if(tipo == 0) dataActivityViewM.buscarMunicipio()
                if(tipo == 1) dataRescateP.municipio = binding.spinnerPuntoR.text.toString()
            }
//            central de autobuses
            3 -> {
                dataRescateP = TipoRescate()
                dataRescateP.centralAutobus = true
                if(tipo == 0) dataActivityViewM.buscarEstacionAuto()
                if(tipo == 1) dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            }
//            ferroca
            4 -> {
                dataRescateP = TipoRescate()
                dataRescateP.ferrocarril = true
                if(tipo == 0) dataActivityViewM.buscarFerroviario()
                if(tipo == 1) dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            }
//            hotel
            5 -> {
                dataRescateP = TipoRescate()
                dataRescateP.hotel = true
                if(tipo == 0) dataActivityViewM.buscarMunicipio()
                if(tipo == 1) dataRescateP.municipio = binding.spinnerPuntoR.text.toString()
            }
//            puestos
            6 -> {
                dataRescateP = TipoRescate()
                dataRescateP.puestosADispo = true
                if(tipo == 0) showPopUp1(prefManager.getNomTipoRescate()!!)
                if(tipo == 1) dataRescateP.municipio = binding.spinnerPuntoR.text.toString()
            }
//            voluntarios
            7 -> { }
//            otros
            8 -> {
                dataRescateP = TipoRescate()
                dataRescateP.otro = true
                if(tipo == 0) dataActivityViewM.buscarOtros()
                if(tipo == 1) dataRescateP.puntoEstra = binding.spinnerPuntoR.text.toString()
            }
            else -> { }
        }
    }

    private fun onTimeSelected(time: String){
        binding.editTextHora.setText(time)
        binding.editTextHora.error = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
//                bindings.LLPresuntosD.visibility = View.VISIBLE

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
            dialog.dismiss()
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

                binding.spinnerTipoIcon.visibility = View.GONE
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
                            binding.spinnerTipo.selectItemByIndex(prefManager.getTipoRescate()!!)
                            binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                            binding.LLPuntoRescate.visibility = View.GONE
                            dataActivityViewM.numerosFamilias.value = emptyList()
                            dataActivityViewM.datosIso.value = emptyList()
                            dataActivityViewM.delAllDatos()
                            prefManager.setPuntoRevision("")
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
                        binding.spinnerTipo.selectItemByIndex(prefManager.getTipoRescate()!!)
                        binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                        binding.LLPuntoRescate.visibility = View.GONE
                        dataActivityViewM.numerosFamilias.value = emptyList()
                        dataActivityViewM.datosIso.value = emptyList()
                        dataActivityViewM.delAllDatos()
                        prefManager.setPuntoRevision("")
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
                        binding.spinnerTipo.selectItemByIndex(prefManager.getTipoRescate()!!)
                        binding.spinnerPuntoR.setText(prefManager.getPuntoRevision())
                        binding.LLPuntoRescate.visibility = View.GONE
                        dataActivityViewM.numerosFamilias.value = emptyList()
                        dataActivityViewM.datosIso.value = emptyList()
                        dataActivityViewM.delAllDatos()
                        prefManager.setPuntoRevision("")
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
                binding.spinnerTipo.selectItemByIndex(prefManager.getTipoRescate()!!)
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
//        binding.spinnerTipo.selectItemByIndex(prefManager.getTipoRescate()!!)
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

//        val adapterSpinner = ArrayAdapter.createFromResource(
//            requireActivity().applicationContext,
//            R.array.tipo_rescate,
//            android.R.layout.simple_spinner_dropdown_item
//        )