package com.ace.mynote.ui.homepage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ace.mynote.data.local.database.note.NoteDao

class HomePageViewModelFactory(
    private val noteDao: NoteDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageViewModel::class.java)) {
            return HomePageViewModel(noteDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}