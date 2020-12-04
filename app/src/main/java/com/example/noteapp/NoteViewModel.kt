package com.example.noteapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }
//    Deleting functionality using coroutines
    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
//       Under background thread
        repository.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch {
        repository.insert(note)
    }

}