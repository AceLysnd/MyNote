package com.ace.mynote.presentation.ui.createaccount

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.mynote.data.local.database.repository.LocalRepository
import com.ace.mynote.data.local.database.user.AccountEntity
import com.ace.mynote.wrapper.Resource
import kotlinx.coroutines.launch

class CreateAccountViewModel(private val repository: LocalRepository) : ViewModel() {

    val detailDataResult = MutableLiveData<Resource<AccountEntity?>>()
    val updateResult = MutableLiveData<Resource<Number>>()

    fun getAccountById(id: Long) {
        viewModelScope.launch {
            detailDataResult.postValue(repository.getAccountById(id))
        }
    }

    fun registerUser(account: AccountEntity) {
        viewModelScope.launch {
            repository.createAccount(account)
        }
    }
    fun updateUser(account: AccountEntity) {
        viewModelScope.launch {
            updateResult.postValue(repository.updateAccount(account))
        }
    }
}