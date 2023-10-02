package com.example.electrorui.usecase

import android.util.Log
import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.UsuarioEntity
import com.example.electrorui.usecase.model.User
import javax.inject.Inject

class GetVerifyUserUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(user: User): User {
        val validUser = repository.getUserFromApi(user)

//        Log.e("datos usuario API", validUser.toString())

        return if (listOf(validUser).isNotEmpty()){
            if(validUser.password.equals("ok")){
                repository.deleteAllUsers()
                val newUser = UsuarioEntity(
                    nickname = validUser.nickname,
                    nombre = validUser.nombre,
                    apellido = validUser.apellido,
                    password = user.password,
                    estado = validUser.estado,
                    tipo = validUser.tipo
                    )
                repository.insertUser(listOf(newUser))
                newUser
            }
            validUser
        } else{
            val nullUser = User("", "", "", "", "", "")
            nullUser
        }
    }
}