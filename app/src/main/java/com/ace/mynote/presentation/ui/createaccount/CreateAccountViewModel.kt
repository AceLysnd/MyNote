package com.ace.mynote.presentation.ui.createaccount

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel

class CreateAccountViewModel(context: Context) : ViewModel() {
    private val sharedPreferences : SharedPreferences = context.getSharedPreferences(NAME, MODE)

    companion object {
        const val NAME = "sharedPref"
        const val MODE = Context.MODE_PRIVATE
    }

    fun setAccount(username:String, password:String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()
    }
}