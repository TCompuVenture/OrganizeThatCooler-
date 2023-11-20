package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScanOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_out_complete) //How you set what layout runs. Can prob. dump rest of this code.
        val bundle = intent.extras
        val s = bundle!!.getString("key1", "No value from MainActivity :(")
        var textbox: TextView = findViewById(R.id.barcode2)
        textbox.text = s
        var toMenu: Button = findViewById<Button>(R.id.home1)
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context
        }
    }
}