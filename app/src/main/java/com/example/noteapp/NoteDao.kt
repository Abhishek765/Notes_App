package com.example.noteapp

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    //    Creating a Dao Insert function to insert new notes with Ignore the duplications
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: Note)

    //    Creating a Dao Delete function to Delete Notes
    @Delete
    suspend fun delete(note: Note)

    //    To Display the notes
    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>


}
