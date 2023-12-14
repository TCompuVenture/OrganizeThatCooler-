package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

// This Activity will be used as a completion screen after the user has added an item to the fridge/freezer
class ScanComplete : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var buttonHasBeenClicked = false
        val bundle = intent.extras
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_in_complete) //How you set what layout runs. Can prob. dump rest of this code.
        var toMenu: Button = findViewById<Button>(R.id.home1)
        val completionMesage: TextView = findViewById<TextView>(R.id.textView4)
        val inOrOut = bundle!!.getString("inOrOut", "No value from MainActivity :(")
        completionMesage.text = "Scan " + inOrOut + " Complete"
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                buttonHasBeenClicked = true
            }
        }
        //This begins a timer that after a specified time in ms it will return to the menu
        //boolean variable was added so as to not return to the menu after the user has already hit the button back to the men
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

            }
            override fun onFinish() {
                if(!buttonHasBeenClicked){
                    toMenu.performClick()
                }
            }
        }.start()
    }
}