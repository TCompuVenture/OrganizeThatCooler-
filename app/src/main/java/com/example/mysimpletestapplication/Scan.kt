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
        var rawUPC : String = "0";
        if(barcodeData != null)
        {
            var rawUPC = barcodeData;

        }
        else
        {
            rawUPC = "0"
        }
        var UPC = rawUPC.toInt() //This line keeps throwing a NumberFormatException. Why???????

        /*********************
         *
         * THis is the error line!
         ********************/
        val item = UPC.let { db.getNoteByUPC(it) } //What is the it doing here?

        if (item != null && UPC != 0) {
            if(item.upc < 0) {
                if (s.compareTo("In").equals(0)) { //adds !! to fix error. Hope not a problem :)
                    Intent(this, AddItem::class.java).also {
                        Toast.makeText(
                            this,
                            "Headed to AddItem!",
                            Toast.LENGTH_SHORT
                        ).show()
                        val bundle1 = Bundle()
                        bundle1.putString("key1", barcodeData)
                        it.putExtras(bundle1)
                        startActivity(it)
                    }
                }

                if (s.compareTo("Out").equals(0)) { //adds !! to fix error. Hope not a problem :)
                    Toast.makeText(
                        this,
                        "It is impossible to scan something in that does not exist, sir!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            else if (UPC == 0)
            {
                Toast.makeText(
                    this,
                    "Error reading bar code.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else
            {
                Intent(this, UpdateActivity::class.java).also {
                    val bundle1 = Bundle()
                    bundle1.putString("key1", barcodeData)
                    it.putExtras(bundle1)
                    startActivity(it)
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
            }
        }

}}