package com.ace.mynote.presentation.ui.noteform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.mynote.data.local.database.dao.NoteDao
import com.ace.mynote.data.local.database.entity.NoteEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NoteFormViewModel(private var noteDao: NoteDao) : ViewModel() {

    val detailDataResult = MutableLiveData<NoteEntity?>()
    val insertResult = MutableLiveData<Number>()
    val updateResult = MutableLiveData<Number>()

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            delay(1000)
            detailDataResult.postValue(noteDao.getNoteById(id))
        }
    }

    fun insertNewNote(item: NoteEntity) {
        viewModelScope.launch {
            delay(1000)
            insertResult.postValue(noteDao.insertNote(item))
        }
    }

    fun updateNote(item: NoteEntity) {
        viewModelScope.launch {
            delay(1000)
            updateResult.postValue(noteDao.updateNote(item))
        }
    }
}