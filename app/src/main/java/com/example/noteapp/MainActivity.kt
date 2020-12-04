package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val NoteAdapter = NotesRVAdapter(this, this)
        recyclerView.adapter = NoteAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer { list ->
            list?.let {
//        Updating the activity
                NoteAdapter.updateNoteList(it as ArrayList<Note>)
                if (it.isEmpty()) {
                    tv_no_notes.visibility = View.VISIBLE
                } else {
                    tv_no_notes.visibility = View.INVISIBLE
                }
            }
        })


    }

    override fun onItemClicked(note: Note) {
        Toast.makeText(this, "${note.text} Deleted", Toast.LENGTH_SHORT).show()
//        Delete the note
        viewModel.deleteNote(note)
    }

    fun submitNote(view: View) {
        val noteText = et_input.text.toString()
        et_input.setText("")

        if (noteText.isNotEmpty()) {
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this, "$noteText Inserted", Toast.LENGTH_SHORT).show()

        }
    }
}