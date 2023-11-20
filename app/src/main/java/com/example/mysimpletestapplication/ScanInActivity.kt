package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ScanInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var buttonHasBeenClicked = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scan_in_complete) //How you set what layout runs. Can prob. dump rest of this code.
        var toMenu: Button = findViewById<Button>(R.id.home1)
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                buttonHasBeenClicked = true
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context
        }
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