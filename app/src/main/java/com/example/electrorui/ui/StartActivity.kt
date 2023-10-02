package com.example.electrorui.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.electrorui.db.PrefManager
import com.example.electrorui.R
import com.example.electrorui.databinding.ActivityStartBinding
import com.example.electrorui.ui.viewModel.StartActivityVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding
    private lateinit var prefManager: PrefManager

    private val dataActivityVM : StartActivityVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
//        binding.buttonL.setOnClickListener{
////            Toast.makeText(this, "Funcion en mantenimiento", Toast.LENGTH_LONG).show()
//
//            val mainActivity = Intent(applicationContext, MainActivity::class.java)
//            startActivity(mainActivity)
//            finish()
//        }

//        Icono de abrir y cerrar ojo
        binding.icVisibility.setOnClickListener {
            binding.icVisibility.setActivated(!binding.icVisibility.isActivated)
            if (binding.icVisibility.isActivated){
                binding.icVisibility.setImageResource(R.drawable.ic_visibility_off_24)
                binding.editTextPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                binding.icVisibility.setImageResource(R.drawable.ic_visibility_on_24)
                binding.editTextPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
        }

        dataActivityVM.isLoading.observe(this){
            binding.progressB.isVisible = it
        }

        dataActivityVM.usuario.observe(this){
            if (!it.nickname.equals("error") and !it.password.equals("error")){
                prefManager.setLogin(true)
                prefManager.setNickname(it.nickname.toString())
                prefManager.setUsername("${it.nombre} ${it.apellido}")
                prefManager.setState(it.estado)
                Toast.makeText(applicationContext, "Bienvenido", Toast.LENGTH_SHORT).show()
                dataActivityVM.nextPage.value = true
            } else{
                Toast.makeText(applicationContext, "Usuario o Contraseña incorrecto", Toast.LENGTH_SHORT).show()
            }
        }

        dataActivityVM.nextPage.observe(this){
            if (it){
                finishAffinity()
                startActivity(Intent(applicationContext, SplashScreen::class.java))
            }else{

            }
        }

        binding.buttonL.setOnClickListener {
            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
            val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

            val userEt = binding.editTextUser.text.toString()
            val passEt = binding.editTextPass.text.toString()

            prefManager.setPass(passEt)

            if (!userEt.isEmpty()){
                if (!passEt.isEmpty()){

                    if(isConnected){
                        dataActivityVM.verifyUserVM(userEt, passEt)
                    } else{
                        Toast
                            .makeText(applicationContext, "Conectate a internet para continuar", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast
                        .makeText(applicationContext, "Rellena el campo de usuario", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast
                    .makeText(applicationContext, "Rellena el campo de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init(){
        prefManager = PrefManager(this)
    }

//    private fun logUser(user : String, pass : String){
//
//        val infoEnviar = LoginModel(user, "", "", pass, "", "")
//        val retrofitData = LoginDBClient.service.verificarUsuario(infoEnviar)
//
//        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
//        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true
//
//        if(isConnected) {
//            retrofitData.enqueue(object : Callback<LoginModel?> {
//                override fun onResponse(
//                    call: Call<LoginModel?>,
//                    response: Response<LoginModel?>
//                ) {
//
//                    if(!response.isSuccessful){
//                        Toast.makeText(applicationContext, "Code: ${response.code()}", Toast.LENGTH_SHORT).show()
//                        return
//                    }
//
//                    val responseBody : LoginModel = response.body()!!
//
//                    if(!responseBody.nickname.equals("error") and !responseBody.password.equals("error")){
////                    Toast.makeText(applicationContext, responseBody.toString(), Toast.LENGTH_LONG).show()
//                        prefManager.setLogin(true)
//                        prefManager.setNickname("${responseBody.nickname}")
//                        prefManager.setUsername("${responseBody.nombre} ${responseBody.apellido}")
//                        prefManager.setPass(pass)
//                        prefManager.setState(responseBody.estado)
////                    Toast.makeText(applicationContext, "Bienvenido", Toast.LENGTH_SHORT).show()
//                        finishAffinity()
//                        startActivity(Intent(applicationContext, MainActivity::class.java))
//                    }else{
//                        prefManager.setLogin(false)
//                        Toast.makeText(applicationContext, "UsuarioEntity o Contraseña incorrecto", Toast.LENGTH_SHORT).show()
//
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginModel?>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            })
//        } else {
//            Toast.makeText(applicationContext, "¡Conéctate a Internet para ingresar!", Toast.LENGTH_LONG).show()
//        }
//
//    }
}