package com.example.kotlin_zem.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ZemCompletion::class], version = 2)
abstract class ZemCompletionDB: RoomDatabase() {
    abstract fun ZemCompletionDao(): ZemCompletionDao

    companion object{
        private var INSTANCE: ZemCompletionDB? = null

        fun getInstance(context: Context): ZemCompletionDB?{
            if(INSTANCE == null){
                synchronized(ZemCompletionDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ZemCompletionDB::class.java,"zemcompletion.db")
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