package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mysimpletestapplication.databinding.ActivityDataViewMainBinding

class ScanInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataViewMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_in) //How you set what layout runs. Can prob. dump rest of this code.
        val bundle = intent.extras
        val s = bundle!!.getString("key1", "No value from MainActivity :(")
        var textbox: TextView = findViewById(R.id.barcode)
        textbox.text = s
        var toMenu: Button = findViewById<Button>(R.id.toMenu3)
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context
        }
        binding = ActivityDataViewMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addButton.setOnClickListener {

            val intent = Intent(this, AddNote::class.java ).also {
                val bundle1 = Bundle()
                bundle1.putString("key1", s)
                it.putExtras(bundle1)
            }
            startActivity(intent)
        }

        binding.addButton.performClick()
    }
}