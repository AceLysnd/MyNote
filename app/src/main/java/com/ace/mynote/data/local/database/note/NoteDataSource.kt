package com.ace.mynote.data.local.database.note

import com.ace.mynote.data.local.database.user.AccountDao
import com.ace.mynote.data.local.database.user.AccountDataSource
import com.ace.mynote.data.local.database.user.AccountEntity

interface NoteDataSource {
    suspend fun getNoteById(id: Int): NoteEntity?

    suspend fun insertNote(item: NoteEntity): Long

    suspend fun updateNote(item: NoteEntity): Int


}

class NoteDataSourceImpl(private val noteDao: NoteDao): NoteDataSource {

    override suspend fun getNoteById(id: Int): NoteEntity? {
        return noteDao.getNoteById(id)
    }

    override suspend fun insertNote(item: NoteEntity): Long {
        return noteDao.insertNote(item)
    }

    override suspend fun updateNote(item: NoteEntity): Int {
        return noteDao.updateNote(item)
    }
}
