package com.example.kotlin_zem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Zem::class], version = 1)
abstract class ZemDB: RoomDatabase() {
    abstract fun ZemDao(): ZemDao

    companion object{
        private var INSTANCE: ZemDB? = null

        fun getInstance(context: Context): ZemDB?{
            if(INSTANCE == null){
                synchronized(ZemDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ZemDB::class.java,"zem.db")
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