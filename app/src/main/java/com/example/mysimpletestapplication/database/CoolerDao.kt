package com.example.mysimpletestapplication.database

import android.content.ClipData.Item
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoolerDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(coolerItem : Item)
@Query("SELECT * FROM cooler_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Cooler>>
}