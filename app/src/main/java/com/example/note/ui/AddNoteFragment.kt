package com.example.note.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.note.R
import com.example.note.application.NoteApplication
import com.example.note.database.Note
import com.example.note.database.getCurrentTime
import com.example.note.database.getTimeFormat
import com.example.note.databinding.FragmentAddNoteBinding
import com.example.note.viewmodel.NoteViewModel
import com.example.note.viewmodel.NoteViewModelFactory

/** [AddNoteFragment] Tạo một ghi chú mới, mở ghi chú cũ */

class AddNoteFragment : Fragment() {

    private val navigationArgs: AddNoteFragmentArgs by navArgs()

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory(
            (activity?.application as NoteApplication).database.noteDao()
        )
    }

    lateinit var note: Note

    private lateinit var binding: FragmentAddNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.time.text = getCurrentTime()
        val id = navigationArgs.noteId

        if (id > 0) {
            binding.topAppBar.menu
                .findItem(R.id.delete).isVisible = true

            viewModel.getNote(id).observe(this.viewLifecycleOwner) {
                note = it
                bind(note)
            }

            binding.topAppBar.menu.findItem(R.id.delete)
                .setOnMenuItemClickListener {
                    onDelete()
                    true
                }
        } else {
            binding.topAppBar.menu.findItem(R.id.save)
                .setOnMenuItemClickListener {
                    addNote()
                    true
                }
        }

        binding.topAppBar.setNavigationOnClickListener {
            back()
        }

    }

    private fun bind(note: Note) {
        binding.apply {
            title.setText(note.title)
            content.setText(note.content)
            time.text = note.getTimeFormat()

            topAppBar.menu.findItem(R.id.save)
                .setOnMenuItemClickListener {
                    updateNote()
                    true
                }
        }
    }

    private fun addNote() {
        viewModel.addNewNote(
            binding.title.text.toString(),
            binding.content.text.toString()
        )
        back()
    }

    private fun updateNote() {
        viewModel.updateNote(
            this.navigationArgs.noteId,
            this.binding.title.text.toString(),
            this.binding.content.text.toString()
        )
        back()
    }

    private fun onDelete() {
        viewModel.delete(note)
        back()
    }

    private fun back() = findNavController()
        .navigate(R.id.action_addNoteFragment_to_homeFragment)
}