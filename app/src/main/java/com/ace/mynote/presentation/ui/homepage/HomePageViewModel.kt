package com.ace.mynote.presentation.ui.homepage

import androidx.lifecycle.ViewModel
import com.ace.mynote.data.repository.LocalRepository

class HomePageViewModel(private val repository: LocalRepository) : ViewModel() {

    fun checkIfAppKeyIsExist(): Boolean {
        return repository.checkIfAppKeyIsExist()
    }
    fun checkIfAppKeyCorrect(appKey: String): Boolean {
        return repository.checkIsAppKeyCorrect(appKey)
    }

}