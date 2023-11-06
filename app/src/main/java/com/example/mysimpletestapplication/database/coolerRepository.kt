package com.example.mysimpletestapplication.database

import android.content.ClipData.Item
import androidx.lifecycle.LiveData

//This thingy abstracts access to multiple data sources. Not strictly necessary, but a good idea to use. And, my tutorial is using it.

class coolerRepository(private val coolerDao: CoolerDao) {
    val readAllData: LiveData<List<Cooler>> = coolerDao.readAllData()

    suspend fun addCooler(coolerItem: Item)
    {
        coolerDao.addItem(coolerItem)
    }
}