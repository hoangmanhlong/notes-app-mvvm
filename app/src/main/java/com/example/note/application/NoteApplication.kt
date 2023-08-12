package com.example.note.application

import android.app.Application
import com.example.note.database.NoteDatabase

class NoteApplication: Application() {
    val database: NoteDatabase by lazy {
        NoteDatabase.getDatabase(this)
    }
}