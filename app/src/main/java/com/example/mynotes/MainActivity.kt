package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val noteList = mutableListOf<Note>()
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val settingsButton = findViewById<ImageButton>(R.id.settings_button)
        val addNoteButton = findViewById<FloatingActionButton>(R.id.add_note_button)

        settingsButton.setOnClickListener {
            Intent(this, SettingsActivity::class.java).also {
                startActivity(it)
            }
        }

        val adapter = NoteAdapter(noteList)
        val myRecyclerView = findViewById<RecyclerView>(R.id.rvNotes)

        myRecyclerView.adapter = adapter
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        val addFragment = fragment_add()

        addNoteButton.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                addFragment.setSharedDataList(noteList, adapter)
                replace(R.id.fragment_container, addFragment)
                addToBackStack(null)
                addNoteButton.visibility = View.GONE
                commit()

            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        findViewById<FloatingActionButton>(R.id.add_note_button).visibility = View.VISIBLE

    }

}
