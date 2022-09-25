package com.ace.mynote.presentation.ui.homepage

import android.app.Activity
import android.app.Application
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.ace.mynote.R
import com.ace.mynote.data.local.database.dao.NoteDao
import com.ace.mynote.data.local.database.entity.NoteEntity

class HomePageViewModel(
    val noteDao: NoteDao,
    application: Application
) : AndroidViewModel(application) {


    fun getNoteList() : MutableLiveData<List<NoteEntity>>{
        val noteListResult = MutableLiveData<List<NoteEntity>>()
        noteListResult.value = noteDao.getAllNotes()
        return noteListResult
    }

    fun deleteNote(item: NoteEntity) {
        noteDao.deleteNote(item)
    }


    fun onDeleteMenuClicked(item: NoteEntity) {
        deleteNote(item)
    }

    fun onEditMenuClicked(item: NoteEntity) {
        TODO("Not yet implemented")
    }
}