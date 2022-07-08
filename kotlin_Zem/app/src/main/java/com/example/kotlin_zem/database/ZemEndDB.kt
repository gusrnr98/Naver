package com.example.kotlin_zem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ZemEnd::class], version = 3)
abstract class ZemEndDB: RoomDatabase() {
    abstract fun ZemEndDao(): ZemEndDao

    companion object{
        private var INSTANCE: ZemEndDB? = null

        fun getInstance(context: Context): ZemEndDB?{
            if(INSTANCE == null){
                synchronized(ZemEndDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ZemEndDB::class.java,"zemend.db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}