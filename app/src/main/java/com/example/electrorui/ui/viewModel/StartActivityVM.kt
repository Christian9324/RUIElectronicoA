package com.example.electrorui.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.electrorui.usecase.GetVerifyUserUC
import com.example.electrorui.usecase.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartActivityVM @Inject constructor(
    private var getVerifyUserUC: GetVerifyUserUC,
) : ViewModel(){

    val nicknameData by lazy { MutableLiveData<String>() }
    val passwordData by lazy { MutableLiveData<String>() }
    val isLoading by lazy { MutableLiveData<Boolean>() }
    val nextPage by lazy { MutableLiveData<Boolean>() }
    val usuario by lazy { MutableLiveData<User>() }

    fun verifyUserVM(nickname: String, pass : String){
        viewModelScope.launch {

            val passUser = User(nickname,"", "", pass, "", "")

            isLoading.value = true
            val result = getVerifyUserUC(passUser)
            if (result.password.equals("ok")){
                usuario.value = result
                isLoading.value = false
            } else{
                usuario.value = result
                isLoading.value = false
            }
        }
    }

    init {
        nextPage.value = false
    }

}