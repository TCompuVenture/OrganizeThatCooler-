package com.example.mysimpletestapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class MainActivity : ComponentActivity() {
    //Creates an instance of our database class
    private lateinit var myDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen) //How you set what layout runs. Can prob. dump rest of this code.
        var toView: Button = findViewById<Button>(R.id.toView)
        var toScanIn: Button = findViewById<Button>(R.id.toScanIn)
        var toScanOut: Button = findViewById<Button>(R.id.toScanOut)
       //TODO: Remove - Old code for testing!
        // var test: Button = findViewById<Button>(R.id.passBundle)
       // var pushToFirebase: Button = findViewById<Button>(R.id.pushToFirebase)


        //ACTUALLY creates the instance of our database worker thingy
        myDB = Firebase.database.reference

        //returns you a new key for your new object!

        //To store a new item in the firebase
        //1. get the table / entity / class
        //database.child("classname")

        //Grab everything with message tag and store it in the query object
        //var query = myDB.child("message.orderByKey()")
        //database.child("Message").child("longFancyKeyHere").child("text").setValue("my message")

        //Need to use an event listener b/c we need to wait for the data
        //Every snap (snapshot) is a .json object. --> it is the query result
//        for (key in snapshot.key)
//        {
//            var msg = snapshot[key]
//        }

       // val key = myDB.child("message").push().key

        toView.setOnClickListener {
            // TODO: Do we remove this edited code?
           // var textView: TextView = findViewById<TextView>(R.id.GPS_x_TextView)
          //  textView.text = "What hath you done?"
            Intent(this, DataViewMain::class.java).also {

                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
        }       //Also refers to previous context

        toScanIn.setOnClickListener{
            Intent(this, Scan::class.java).also {
                var bundle2 = Bundle()
                bundle2.putString("key1", "In")
                //passing in a bundle to tell the Scan screen which screen to go after scanning a barcode
                it.putExtras(bundle2)
                startActivity(it)
            }
        }
//        activity_date_view_main_access.setOnClickListener {
//            Intent(this, DataViewMain::class.java).also {
//                startActivity(it)
//            }
//        }

        toScanOut.setOnClickListener{
            Intent(this, Scan::class.java).also {
                var bundle1 = Bundle()
                bundle1.putString("key1", "Out")
                //passing in a bundle to tell the Scan screen which screen to go after scanning a barcode
                it.putExtras(bundle1)
                startActivity(it)
            }
        }

        //TODO: Remove this code! It was for testing Firebase / bundle passing. We don't need it anymore! Just kept in case we ever need to look back at it.
//        test.setOnClickListener{
//
//                Intent(this, SecondActivity::class.java).also {
//                   // bundle.putString("key1", "Passing Bundle From Main Activity to 2nd Activity")
//                    var bundle = Bundle()
//                    bundle.putString("key1", "Passing Bundle From Main Activity to 2nd Activity")
//                    it.putExtras(bundle)
//                    startActivity(it)
//                }
//        }
//        pushToFirebase.setOnClickListener{
//
//            val key = myDB.child("message").push().key
//            if (key!=null){
//                //myDB.child("new message").child(key).setValue("myThingy")
//                writeNewMessage(key, "Hellooooooo there!", Date().toString())
//            }
//
//          //  textView.text = Date().toString()
//            var query = myDB.child("message").orderByKey()
//            query.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    //Log.d(TAG, "here")
//                    for (snap in snapshot.children){
//                        val msg = snap.getValue<Message>()
//                        val date = msg?.datetime
//                        Log.d(ContentValues.TAG, "Value is: $date")
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//            })
//
//            Intent(this, SecondActivity::class.java).also {
//                var bundle = Bundle()
//                bundle.putString("key", key)
//                // clearing the bundle
//                it.putExtras(bundle)
//
//                startActivity(it)
//            }
//        }
//    }
//    fun writeNewMessage(msgId: String, text: String, timestamp: String){
//        val msg = Message(text, timestamp)
//        myDB.child("message").child(msgId).setValue(msg)
    }
}
