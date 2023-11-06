package com.example.mysimpletestapplication.database

import android.app.Application
import android.content.ClipData.Item
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//The ViewModel provides data to the UI and survives configuration changes. A ViewModel acts as a
//communication center between the Repository and the UI (Helpful video tutorial series, 1)
class CoolerViewModel(application: Application): AndroidViewModel(application) {
    private val readAllData: LiveData<List<Cooler>>
    private val repository: coolerRepository
    init { //Executed when CoolerViewModel is called
        val coolerDao = CoolerDatabase.getDatabase(application).coolerDao()
        repository = coolerRepository(coolerDao)
        readAllData = repository.readAllData
    }
    fun addItem(coolerItem: Item)
    {
        viewModelScope.launch(Dispatchers.IO) //runs this code in a background thread
        {
            repository.addCooler(coolerItem) //
        }
    }

}