package com.ace.mynote.presentation.ui.noteform

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ace.mynote.data.local.database.dao.NoteDao

//class NoteFormViewModelFactory
//    (private val noteDao: NoteDao,
//        private val application: Application
//    ) : ViewModelProvider.Factory {
//        @Suppress("unchecked_cast")
//        override fun <T : ViewModel> create(modelClass: Class<T>): T {
//            if (modelClass.isAssignableFrom(NoteFormViewModel::class.java)) {
//                return NoteFormViewModel(noteDao, application) as T
//            }
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//    }
//}