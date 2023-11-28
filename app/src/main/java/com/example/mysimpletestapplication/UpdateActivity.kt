package com.example.mysimpletestapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mysimpletestapplication.databinding.UpdateItemOldDropBinding

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: UpdateItemOldDropBinding
    private lateinit var db: ItemDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateItemOldDropBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = ItemDatabaseHelper(this)

        noteId = intent.getIntExtra("note_id", -1)
        if(noteId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(noteId)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.upc.toString())

        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString()
            val newUPCtemp = binding.updateContentEditText.text.toString()
            val newUPC = newUPCtemp.toLong()
            val updateItem = Item(noteId, newTitle, newUPC, 1) //Passing default value for now
            db.updateItem(updateItem)
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()

        }
    }
}