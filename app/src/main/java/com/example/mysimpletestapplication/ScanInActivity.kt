package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScanInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_in) //How you set what layout runs. Can prob. dump rest of this code.
        var toMenu: Button = findViewById<Button>(R.id.toMenu)
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context
        }
    }
}