package com.example.note.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.note.R
import com.example.note.adapter.NoteAdapter
import com.example.note.application.NoteApplication
import com.example.note.databinding.FragmentHomeBinding
import com.example.note.viewmodel.NoteViewModel
import com.example.note.viewmodel.NoteViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class HomeFragment : Fragment() {

    private val viewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory(
            (activity?.application as NoteApplication).database.noteDao()
        )
    }

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeFragment = this@HomeFragment

        val adapter = NoteAdapter {
            val action = HomeFragmentDirections
                .actionHomeFragmentToAddNoteFragment(it.id)
            findNavController().navigate(action)
        }
        binding.recyclerView.adapter = adapter

        viewModel.getNotes().observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.topAppBar.menu.findItem(R.id.info).setOnMenuItemClickListener {
            showDialog()
            true
        }

    }

    fun onAddNoteButton() {
        val action = HomeFragmentDirections
            .actionHomeFragmentToAddNoteFragment(-1)
        findNavController().navigate(action)
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.info))
            .setMessage(getString(R.string.info_app))
            .setPositiveButton(getString(R.string.ok)) { _, _ -> }
            .show()
    }
}