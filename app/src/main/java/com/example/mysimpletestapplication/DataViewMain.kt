package com.example.mysimpletestapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysimpletestapplication.databinding.ActivityDataViewMainBinding

class DataViewMain : AppCompatActivity() {
    private lateinit var binding: ActivityDataViewMainBinding
    private lateinit var db:NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataViewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter


//        binding.addButton.setOnClickListener {
//            val intent = Intent(this, AddNote::class.java )
//            startActivity(intent)
//        }
        //Hides the add button from the view notes screen so that user isn't confused. Can't delete for some reason, but hey! It's gone!
        binding.addButton.setVisibility(View.GONE);

        var backButton: Button = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)

        }


    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}