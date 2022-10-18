package com.ace.mynote.ui.noteform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ace.mynote.data.local.database.note.NoteEntity
import com.ace.mynote.data.local.database.repository.LocalRepository
import com.ace.mynote.wrapper.Resource
import kotlinx.coroutines.launch

class NoteFormViewModel(private val repository: LocalRepository) : ViewModel() {

    val detailDataResult = MutableLiveData<Resource<NoteEntity?>>()
    val insertResult = MutableLiveData<Resource<Number>>()
    val updateResult = MutableLiveData<Resource<Number>>()

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            detailDataResult.postValue(repository.getNoteById(id))
        }
    }

    fun insertNewNote(item: NoteEntity) {
        viewModelScope.launch {
            insertResult.postValue(repository.insertNote(item))
        }
    }

    fun updateNote(item: NoteEntity) {
        viewModelScope.launch {
            updateResult.postValue(repository.updateNote(item))
        }
    }
}