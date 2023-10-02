package com.example.electrorui.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.example.electrorui.db.PrefManager
import com.example.electrorui.databinding.ActivitySplashScreenBinding
import com.example.electrorui.ui.viewModel.SplashScreen_AVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var prefManager: PrefManager
    private val dataActivityViewM : SplashScreen_AVM by viewModels()

    var contar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected : Boolean = activeNetwork?.isConnectedOrConnecting == true

        prefManager.setConnection(isConnected)
        prefManager.setvistasPopUpInternet(false)

        dataActivityViewM.conectadoInternet.value = isConnected

        dataActivityViewM.porcentProgress.observe(this){
            binding.progressBar.progress = it
            binding.tvProcent.setText("${it}%")
        }

        dataActivityViewM.statusMessage.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        dataActivityViewM.continuar.observe(this){
            if (it == 1){
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
                finish()
            } else if( it == 2){
                prefManager.removeData()
                val loginIntent = Intent(this@SplashScreen, StartActivity::class.java)
                startActivity(loginIntent)
                finish()
            } else {

            }
        }

        dataActivityViewM.oficinaRepresentacion.observe(this){
            prefManager.setState(it)
        }

        dataActivityViewM.onCreate()

    }

    private fun init(){
        prefManager = PrefManager(this)
    }
}