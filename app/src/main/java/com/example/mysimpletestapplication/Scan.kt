package com.example.mysimpletestapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import java.io.IOException

//This Activity utilizes the Camera of the User's device to read a barcode
//The User will use this to log items that they are adding or removing from their fridge/freezer
class Scan : AppCompatActivity() {
    private var surfaceView: SurfaceView? = null
    private var barcodeDetector: BarcodeDetector? = null
    private var cameraSource: CameraSource? = null
    private val REQUEST_CAMERA_PERMISSION = 201
    private var toneGen1: ToneGenerator? = null
    private var barcodeText: TextView? = null
    private var barcodeData: String? = null
    private var foundBarcode = false
    private lateinit var db: ItemDatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val bundle = intent.extras
        db = ItemDatabaseHelper(this)

        val s = bundle!!.getString("key1", "No value from MainActivity :(")
        //-------------------
        //nextScreen(s, "1")//Get rid of this to test the barcode scanner on your phone
        //-------------------


        var textBox: TextView = findViewById(R.id.InorOut)
        val toMenu: Button = findViewById(R.id.toMenu)
        toMenu.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            } //Before also: defines an instance of an intent in context of our second activity
            //Also refers to previous context
        }
        toneGen1 = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        surfaceView = findViewById<View>(R.id.surface_view) as SurfaceView?
        barcodeText = findViewById<View>(R.id.barcode_text) as TextView?
        initialiseDetectorsAndSources(s, textBox)
    }
    //This function is the code for the Barcode Scanner
    private fun initialiseDetectorsAndSources(s: String,textBox: TextView) {
        barcodeDetector = BarcodeDetector.Builder(this)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build()
        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true) //you should add this feature
            .build()
        surfaceView!!.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(
                            this@Scan,
                            android.Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        cameraSource?.start(surfaceView?.holder)
                    } else {
                        ActivityCompat.requestPermissions(
                            this@Scan,
                            arrayOf(android.Manifest.permission.CAMERA),
                            REQUEST_CAMERA_PERMISSION
                        )
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource?.stop()
            }
        })
        barcodeDetector?.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                // Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems

                if(foundBarcode){
                    return
                } else if (barcodes.size() != 0) {
                    barcodeText!!.post {
                        if (barcodes.valueAt(0).email != null) {
                            barcodeText?.removeCallbacks(null)
                            barcodeData = barcodes.valueAt(0).email.address
                            barcodeText?.text = barcodeData
                            toneGen1!!.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                        } else {
                            barcodeData = barcodes.valueAt(0).displayValue
                            barcodeText?.text = barcodeData
                            toneGen1!!.startTone(ToneGenerator.TONE_CDMA_PIP, 150)
                            nextScreen(s, barcodeData)

                            foundBarcode = true
                            return@post
                        }
                    }
                    return
                }
            }
        })
    }
    //This function loads the next activity based on which screen the bundle says to go to
    private fun nextScreen(s: String, barcodeData: String?){

        var rawUPC : String = "0"; //Declared here with a 0 so that we don't acidentally end up with a null value down the line if the barcode is not scanned correctly.
        if(barcodeData != null)
        {
            rawUPC = barcodeData;
        }
//        else if(barcodeData == null)
//        {
//            rawUPC = "0"
//        }
        var UPC = rawUPC.toLong() //MUST BE A LONG - doesn't fit into an int!

        val item = UPC.let { db.getNoteByUPC(UPC) }

        //The problem with this is I am using intent wrong. Why can I use it this way on MainActivity but not here? Also: Why no error?

        if (item.upc != -1 && UPC != 0.toLong()) { //NOT item = null because an item IS being returned, even when nothing is found in the DB. (An item with all -1s, but an item, nonetheless)
            if (item.upc > 0) {
                if (s.compareTo("In").equals(0)) {
                    Toast.makeText(
                        this,
                        "Headed to update item!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Intent(this, AddItem::class.java).also {
                        val bundle1 = Bundle()
                        bundle1.putString("key1", barcodeData)
                        it.putExtras(bundle1)
                        startActivity(it)
                    }
                }

                if (s.compareTo("Out").equals(0)) {
                    Toast.makeText(
                        this,
                        "It is impossible to scan something in that does not exist, sir!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }
        else if (UPC == 0.toLong())
        {
            Toast.makeText(
                this,
                "Error reading bar code.",
                Toast.LENGTH_SHORT
            ).show()
            //Prob. should restart scan screen at this point.
        }

        else
        {
            Toast.makeText(
                this,
                "Headed to add an item! " + UPC,
                Toast.LENGTH_SHORT
            ).show()

            /******************************************
            This is the last thing to figure out!!! Why isn't this activity launching? All of the logic is correct...
             ******************************************************8*/
            var testButton: Button = findViewById(R.id.testing)

            testButton.setOnClickListener {
                try {
                    Toast.makeText(
                        this,
                        "Button was clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                    Intent(this, AddItem::class.java).also {
                        val bundle1 = Bundle()
                        bundle1.putString("key1", barcodeData)
                        it.putExtras(bundle1)
                        startActivity(it)
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "Error launching UpdateActivity: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
           }

//            testButton.performClick()
//            val intent = Intent(this, UpdateActivity::class.java)
//            val bundle1 = Bundle()
//            bundle1.putString("key1", barcodeData)
//            intent.putExtras(bundle1)
//            startActivity(intent)
//            Toast.makeText(
//                this,
//                "Now past where the activity was supposed to be started" + UPC,
//                Toast.LENGTH_SHORT
//            ).show()

            }
        return
    }

}
//            if (s.compareTo("In").equals(0)){ //adds !! to fix error. Hope not a problem :)
//                Intent(this, AddItem::class.java ).also {
//                    val bundle1 = Bundle()
//                    bundle1.putString("key1", barcodeData)
//                    it.putExtras(bundle1)
//                    startActivity(it)
//                }
//
//            if (s.compareTo("Out").equals(0)){ //adds !! to fix error. Hope not a problem :)
//                Intent(this, ScanOutActivity::class.java ).also {
//                    val bundle2 = Bundle()
//                    bundle2.putString("key1", barcodeData)
//                    it.putExtras(bundle2)
//                    startActivity(it)
//                }
//
//