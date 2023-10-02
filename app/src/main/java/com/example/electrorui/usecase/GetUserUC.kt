package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.db.entityModel.toDB
import com.example.electrorui.usecase.model.User
import javax.inject.Inject

class GetUserUC @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(): User {
        val userDB = repository.getAllUsersFromDB()

        return if (userDB.isNotEmpty()){
            val OneUserDB = userDB[userDB.size - 1]
            OneUserDB
        } else{
            val nullUser = User("", "", "", "", "", "")
            nullUser
        }
    }
}