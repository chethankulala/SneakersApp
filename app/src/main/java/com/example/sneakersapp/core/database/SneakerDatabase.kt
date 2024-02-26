package com.example.sneakersapp.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sneakersapp.features.model.Sneaker

@Database(entities = [Sneaker::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SneakerDatabase : RoomDatabase() {

    abstract fun getSneakerDao(): SneakerDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SneakerDatabase? = null

        fun getDatabase(context: Context): SneakerDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SneakerDatabase::class.java,
                    "MyDatabase.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}