package com.ace.mynote.presentation.ui.homepage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ace.mynote.data.local.database.note.NoteDao
import com.ace.mynote.data.local.database.note.NoteEntity

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