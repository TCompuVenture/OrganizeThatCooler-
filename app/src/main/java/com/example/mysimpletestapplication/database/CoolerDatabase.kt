package com.example.mysimpletestapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Cooler::class], version = 1, exportSchema = false)
abstract class CoolerDatabase: RoomDatabase() {

    //Our Cooler database is in singleton form - there can only ever be one.
    abstract fun coolerDao(): CoolerDao

    companion object{
        @Volatile //Writes to this field immediately made visible
        private var INSTANCE: CoolerDatabase? = null

        fun getDatabase(context: Context): CoolerDatabase{ //Does database exist? If so, return it. Otherwise, create it
            val tempInstance = INSTANCE
            if(tempInstance != null) //Do we already have a DB instance? If so, return it!
            {
                return tempInstance
            }
            //Don't have one? Let's get that created here.
            kotlin.synchronized(this) //Everything in here protected from concurrent execution by multiple threads
            {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CoolerDatabase::class.java,
                    "cooler_database"
                ).build()
                INSTANCE = instance //Assigning instance
                return instance
            }
        }
    }
}