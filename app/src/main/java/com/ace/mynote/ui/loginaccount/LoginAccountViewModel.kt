package com.ace.mynote.ui.loginaccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.mynote.data.local.database.repository.LocalRepository
import com.ace.mynote.data.local.database.user.AccountEntity
import com.ace.mynote.wrapper.Resource
import kotlinx.coroutines.launch

class LoginAccountViewModel(private val repository: LocalRepository) : ViewModel() {

    private var _getUserResult = MutableLiveData<Resource<AccountEntity>>()
    val getUser: LiveData<Resource<AccountEntity>> get() = _getUserResult

    fun getUser(username: String) {
        viewModelScope.launch {
            _getUserResult.postValue(repository.getAccount(username))
        }
    }

}