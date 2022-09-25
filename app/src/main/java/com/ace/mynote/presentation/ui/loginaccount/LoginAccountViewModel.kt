package com.ace.mynote.presentation.ui.loginaccount

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.ace.mynote.presentation.ui.createaccount.CreateAccountViewModel

class LoginAccountViewModel(context: Context) : ViewModel() {

    private val sharedPreferences : SharedPreferences = context.getSharedPreferences(
        CreateAccountViewModel.NAME,
        CreateAccountViewModel.MODE
    )
    fun checkIsUsernameCorrect(username: String): Boolean {
        return getUsername().equals(username, ignoreCase = true)
    }
    fun checkIsPasswordCorrect(password: String): Boolean {
        return getPassword().equals(password, ignoreCase = true)
    }

    private fun getUsername(): String? {
        return sharedPreferences.getString("username","")
    }
    private fun getPassword(): String? {
        return sharedPreferences.getString("username","")
    }
}