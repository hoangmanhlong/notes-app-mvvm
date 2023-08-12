package com.example.note.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.note.database.Note
import com.example.note.database.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteDao: NoteDao
) : ViewModel() {

    fun getNotes(): LiveData<List<Note>> = noteDao.getNotes().asLiveData()

    fun getNote(id: Int): LiveData<Note> = noteDao.getNote(id).asLiveData()

    fun delete(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    fun addNewNote(
        title: String,
        content: String
    ) {
        val note = Note(title = title, content = content)
        viewModelScope.launch(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }

    fun updateNote(
        id: Int,
        title: String,
        content: String,
    ) {
        val note = Note(id, title, content)
        viewModelScope.launch {
            noteDao.update(note)
        }
    }
}

class NoteViewModelFactory(
    private val noteDao: NoteDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
