package com.example.electrorui.usecase

import com.example.electrorui.db.RepositoryApp
import com.example.electrorui.usecase.model.RescateComp
import javax.inject.Inject

class SetRescateCompletoDB @Inject constructor(
    private val repository : RepositoryApp
) {
    suspend operator fun invoke(registo : List<RescateComp>) {
        val validUser = repository.insertRescatesCompletos(registo)
    }
}