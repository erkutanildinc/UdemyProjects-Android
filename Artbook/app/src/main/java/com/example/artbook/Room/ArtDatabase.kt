package com.example.artbook.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArtModel::class], version = 1)
abstract class ArtDatabase : RoomDatabase(){
    abstract fun artDao() : ArtDao

    companion object {
        @Volatile
        private var INSTANCE: ArtDatabase? = null

        fun getInstance(context: Context): ArtDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArtDatabase::class.java,
                    "artDatabase"
                ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                INSTANCE=instance
                instance
            }
        }
    }
}