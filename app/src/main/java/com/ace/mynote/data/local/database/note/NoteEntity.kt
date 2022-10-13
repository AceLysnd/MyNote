package com.ace.mynote.data.local.database.note

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "notes")
class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "note_title")
    var noteTitle: String?,
    @ColumnInfo(name = "note_description")
    var noteDescription: String?,
    @ColumnInfo(name = "note_content")
    var noteContent: String?,
) : Parcelable