package com.ace.mynote.presentation.ui.createaccount

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

    fun registerUser(account: AccountEntity) {
        viewModelScope.launch {
            repository.createAccount(account)
        }
    }
}