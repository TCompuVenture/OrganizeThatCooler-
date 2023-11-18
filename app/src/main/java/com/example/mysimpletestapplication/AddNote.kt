package com.example.mysimpletestapplication

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.mysimpletestapplication.databinding.ActivityAddNoteBinding

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var db: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val s = bundle!!.getString("key1", "No value from MainActivity :(")
        val UPCText : TextView = findViewById<TextView>(R.id.contentEditText)
        UPCText.text = s
        db = NotesDatabaseHelper(this)
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val note = Note(0, title, content)
            db.insertNote(note)
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
            Intent(this, ScanInActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }
    }
}