package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second) //How you set what layout runs. Can prob. dump rest of this code.
        var button: Button = findViewById<Button>(R.id.btnBack)
        button.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context

        val bundle = intent.extras
        var textBox: TextView = findViewById<TextView>(R.id.textView2)

        textBox.text = ("Test")
            //#############################
            //This is the one line that doesn't work! Once we figure it out, we have persistent data between screens!
            //#############################
        //textBox.text = (bundle!!.getString("key1", "No value from MainActivity :("))

        }

    }
}