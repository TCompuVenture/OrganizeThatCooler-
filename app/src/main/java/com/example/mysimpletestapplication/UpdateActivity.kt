package com.example.mysimpletestapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysimpletestapplication.databinding.ActivityAddItemBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private lateinit var db: ItemDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ItemDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.titleEditText.setText(note.title)
        binding.contentEditText.setText(note.upc.toString())



        binding.saveButton.setOnClickListener {
            val newTitle = binding.titleEditText.text.toString()
            val newUPCtemp = binding.contentEditText.text.toString()
            val newUPC = newUPCtemp.toLong()
            val updateItem = Item(noteId, newTitle, newUPC, 1) //Passing default value for now
            db.updateItem(updateItem)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()

        }
    }
}