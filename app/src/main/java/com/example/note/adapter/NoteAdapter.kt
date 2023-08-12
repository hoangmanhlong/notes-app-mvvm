package com.example.note.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.note.database.Note
import com.example.note.database.getTimeFormat
import com.example.note.databinding.ListNotesBinding

class NoteAdapter(
    private var onClicked: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DiffCallback) {

    class NoteViewHolder(
        private var binding: ListNotesBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.apply {
                title.text = note.title
                content.text = note.content
                time.text = note.getTimeFormat()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val viewHolder = NoteViewHolder(
            ListNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onClicked(getItem((position)))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}