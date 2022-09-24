package com.ace.mynote.presentation.ui.createaccount

import androidx.lifecycle.ViewModel
import com.ace.mynote.data.repository.LocalRepository

class CreateAccountViewModel(private val repository: LocalRepository) : ViewModel() {
    fun setAppKey(newAccount: String){
        repository.setUserAccount(newAccount)
    }
}