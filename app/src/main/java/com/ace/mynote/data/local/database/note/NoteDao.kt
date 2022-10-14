package com.ace.mynote.data.local.database.note

import androidx.room.*

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
    fun updateNote(item : NoteEntity) : Int
}