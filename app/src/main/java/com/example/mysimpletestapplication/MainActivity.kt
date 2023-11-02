package com.example.mysimpletestapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mysimpletestapplication.ui.theme.MySimpleTestApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout) //How you set what layout runs. Can prob. dump rest of this code.
        var button: Button = findViewById<Button>(R.id.gps_button)
        button.setOnClickListener {
            var textView: TextView = findViewById<TextView>(R.id.GPS_x_TextView)
            textView.text = "What hath you done?"
            Intent(this, SecondActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
        }       //Also refers to previous context
    }
}
