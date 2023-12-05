package com.example.mysimpletestapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
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

        //noteId = intent.getString("key1", "-1")

        val bundle = intent.extras
        val upc = bundle!!.getString("key1", "-1")
        if(upc == "-1"){
            Toast.makeText(this, "Invalid UPC!!!!!!!!!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val item = db.getNoteByUPC(upc.toLong())
        binding.titleEditText.setText(item.title)
        binding.contentEditText.setText(item.upc.toString())
        binding.addNoteHeading.setText("Id: " + item.id.toString())

        //For quantity buttons
        var quantity: Int = item.qty;
        val qtyTextBox : TextView = findViewById<TextView>(R.id.quantityTextBox)
        var text = "Quantity: $quantity"
        qtyTextBox.text = text

        binding.addButtonAddItem.setOnClickListener {
            quantity++;
            text = "Quantity: $quantity"
            qtyTextBox.text = text
        }
        binding.buttonMinus.setOnClickListener {
            if(quantity > 0)
            {
                quantity--
            }
            text = "Quantity: $quantity"
            qtyTextBox.text = text
        }


        binding.saveButton.setOnClickListener {
            val newTitle = binding.titleEditText.text.toString()
            val newUPCtemp = binding.contentEditText.text.toString()
            val newUPC = newUPCtemp.toLong()
            //val updateItem = Item(noteId, newTitle, newUPC, quantity) //Passing default value for now
            val updateItem = Item(noteId, newTitle, newUPC, quantity)
            //db.deleteItem(item.id) //since update function isn't working I just deleted and reinserted the item
           // db.insertItem(updateItem)

            db.updateItemByUPC(updateItem) //update function doesn't seem to be working
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
            Intent(this, ScanComplete::class.java).also {
                startActivity(it)
            }

        }
    }
}