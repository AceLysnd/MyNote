package com.ace.mynote.presentation.ui.loginaccount

import androidx.lifecycle.ViewModel
import com.ace.mynote.data.repository.LocalRepository

class LoginAccountViewModel(private val repository: LocalRepository) : ViewModel() {
    fun checkIsAppKeyCorrect(appKey: String): Boolean {
        return repository.checkIsAppKeyCorrect(appKey)
    }
}