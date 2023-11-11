package com.example.mysimpletestapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView


class ViewActivity2 : AppCompatActivity(){
    var dataModels: ArrayList<Message>? = null
    var listView: ListView? = null
    private var adapter: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view) //How you set what layout runs. Can prob. dump rest of this code.
        val extras = intent.extras
        val value: String?
        if (extras != null) {
            value = extras.getString("key")
            var textView: TextView = findViewById(R.id.textView2)
            textView.text = value
        }
        var listView = findViewById<ListView>(R.id.myListView)

        dataModels = ArrayList()

        adapter = CustomAdapter(dataModels, applicationContext)
        listView.adapter = adapter

        var toMenu: Button = findViewById<Button>(R.id.toMenu2)
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context
    }
    }

}

