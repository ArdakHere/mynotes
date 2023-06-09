package com.example.mynotes

import android.graphics.Color
import android.media.Image
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.RecyclerView


class NoteAdapter(private val notes: MutableList<Note>) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        private val editButton = itemView.findViewById<ImageButton>(R.id.edit_button)

        init {
            itemView.setOnLongClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val taskToDelete = notes[position]

                    // Display a dialog or confirmation prompt for deletion
                    val builder = AlertDialog.Builder(itemView.context)
                    builder.setTitle("Delete note")
                    builder.setMessage("Are you sure you want to delete this note?")

                    builder.setPositiveButton("Delete") { dialog, which ->
                        // Delete the task from your task list
                        notes.removeAt(position)
                        notifyItemRemoved(position)
                    }

                    builder.setNegativeButton("Cancel", null)

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
                true
            }

            editButton.setOnClickListener {
                val position = absoluteAdapterPosition
                val item = notes[position]

                val fragment = fragment_edit.newInstance(item.title, item.content)

                fragment.setSharedData(notes, this@NoteAdapter, position)

                if (position != RecyclerView.NO_POSITION) {

                    val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.fragment_container, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                }
            }
        }

        fun bind(note: Note) {
            titleTextView.text = note.title
            contentTextView.text = note.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(view)
    }

    // Bind data to ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val EditButton = findViewById<ImageButton>(R.id.edit_button)
            EditButton.bringToFront()

            val titleText = findViewById<TextView>(R.id.titleTextView)
            val contentText = findViewById<TextView>(R.id.contentTextView)
            titleText.text = notes[position].title
            contentText.text = notes[position].content
            contentText.setBackgroundColor(notes[position].color)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}



