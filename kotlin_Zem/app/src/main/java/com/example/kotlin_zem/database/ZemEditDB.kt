package com.example.kotlin_zem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ZemEdit::class], version = 4)
abstract class ZemEditDB: RoomDatabase() {
    abstract fun ZemEditDao(): ZemEditDao

    companion object{
        private var INSTANCE: ZemEditDB? = null

        fun getInstance(context: Context): ZemEditDB?{
            if(INSTANCE == null){
                synchronized(ZemEditDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ZemEditDB::class.java,"zemeidt.db")
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