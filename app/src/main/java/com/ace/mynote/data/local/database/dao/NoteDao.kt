package com.ace.mynote.data.local.database.dao

import androidx.room.*
import com.ace.mynote.data.local.database.entity.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM NOTES")
    fun getAllNotes() : List<NoteEntity>

    @Query("SELECT * FROM NOTES WHERE ID == :id LIMIT 1")
    fun getNoteById(id : Int) : NoteEntity?

    @Insert
    fun insertNote(note : NoteEntity) : Long

    @Delete
    fun deleteNote(note: NoteEntity) : Int

    @Update
    fun updateNote(note : NoteEntity) : Int
}