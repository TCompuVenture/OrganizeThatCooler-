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
        var test: Button = findViewById<Button>(R.id.passBundle)
        var noBundlePass: Button = findViewById<Button>(R.id.noBundlePass)

        toView.setOnClickListener {
           // var textView: TextView = findViewById<TextView>(R.id.GPS_x_TextView)
          //  textView.text = "What hath you done?"
            Intent(this, ViewActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
        }       //Also refers to previous context

        toScanIn.setOnClickListener{
            Intent(this, Scan::class.java).also {
                startActivity(it)
            }
        }

        toScanOut.setOnClickListener{
            Intent(this, ScanOutActivity::class.java).also {
                startActivity(it)
            }
        }
        test.setOnClickListener{

                Intent(this, SecondActivity::class.java).also {
                   // bundle.putString("key1", "Passing Bundle From Main Activity to 2nd Activity")
                    var bundle = Bundle()
                    bundle.putString("key1", "Passing Bundle From Main Activity to 2nd Activity")
                    it.putExtras(bundle)
                    startActivity(it)
                }
        }
        noBundlePass.setOnClickListener{

            Intent(this, SecondActivity::class.java).also {
                var bundle = Bundle()
                bundle.putString("key1", "Not passing Bundle From Main Activity")
                // clearing the bundle
                bundle.clear()
                it.putExtras(bundle)
                startActivity(it)
            }
        }
    }
}
