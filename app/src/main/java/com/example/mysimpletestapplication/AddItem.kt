package com.example.mysimpletestapplication

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.mysimpletestapplication.databinding.ActivityAddItemBinding
import org.w3c.dom.Text

class AddItem : AppCompatActivity() {

    private lateinit var binding: ActivityAddItemBinding
    private lateinit var db: ItemDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.extras
        val s = bundle!!.getString("key1", "No value from MainActivity :(")
        val inOrOut = bundle!!.getString("inOrOut", "No value from MainActivity :(")
        val UPCText : TextView = findViewById<TextView>(R.id.contentEditText)
        UPCText.text = s //Getting UPC from bundle
        db = ItemDatabaseHelper(this)
        val nameTextBox : TextView = findViewById<TextView>(R.id.titleEditText)
        //For quantity buttons
        var quantity: Int = 0;
        val qtyTextBox : TextView = findViewById<TextView>(R.id.quantityTextBox)
        var text = "Quantity: $quantity";
        qtyTextBox.text = text;

        binding.addButtonAddItem.setOnClickListener {
                quantity++;
            text = "Quantity: $quantity";
            qtyTextBox.text = text;
        }
        binding.buttonMinus.setOnClickListener {
            if(quantity > 0)
            {
                quantity--;
            }
            text = "Quantity: $quantity";
            qtyTextBox.text = text;
        }

        nameTextBox.setOnClickListener{
            if(nameTextBox.text.equals("Name")){
                nameTextBox.text = ""
            }
        }


        //TODO: Warn user when saving an item with quantity 0

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val upcTemp = binding.contentEditText.text.toString()
            val upc: Long = upcTemp.toLong()
            if(quantity != 0) {

                val item = Item(
                    0, //probably should auto-increment this!!!
                    title,
                    upc,
                    quantity
                )
                db.insertItem(item)
                Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show()
                Intent(this, ScanComplete::class.java).also {
                    val bundle1 = Bundle()
                    bundle1.putString("inOrOut", inOrOut)
                    it.putExtras(bundle1)
                    startActivity(it)
                }
                finish()
            }
            else {
                Toast.makeText(this, "It is impossible to add a new item with a quantity of 0!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}