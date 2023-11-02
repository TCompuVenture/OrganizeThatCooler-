package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout) //How you set what layout runs. Can prob. dump rest of this code.
        var toView: Button = findViewById<Button>(R.id.toView)
        var toScanIn: Button = findViewById<Button>(R.id.toScanIn)
        var toScanOut: Button = findViewById<Button>(R.id.toScanOut)
        toView.setOnClickListener {
           // var textView: TextView = findViewById<TextView>(R.id.GPS_x_TextView)
          //  textView.text = "What hath you done?"
            Intent(this, ViewActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
        }       //Also refers to previous context

        toScanIn.setOnClickListener{
            Intent(this, ScanInActivity::class.java).also {
                startActivity(it)
            }
        }

        toScanOut.setOnClickListener{
            Intent(this, ScanOutActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}
