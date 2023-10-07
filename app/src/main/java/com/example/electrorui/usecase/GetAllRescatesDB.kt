package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.PuntosInter
import com.example.electrorui.usecase.model.Rescate
import javax.inject.Inject


class GetAllRescatesDB @Inject constructor(
    private val repository : RepositoryApp
){
    suspend operator fun invoke() : List<Rescate> {
        val rescate = repository.getAllRescateDataFromDB()

        return if(!rescate.isNullOrEmpty()){
            rescate
        } else {
            val rescate = Rescate(
                        "", "", "","",
                false,
                false,"","", "", "",false,
                false,
                false,
                false, "",
                false,"",
                false,false,false,false,false,false,false,false,false,false,
                false,
                false,
                false,0,
                "", "",
            )
            listOf(rescate)
        }
    }
}