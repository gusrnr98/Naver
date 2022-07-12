package com.example.kotlin_challenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Challenge::class], version = 1)
abstract class ChallengeDB: RoomDatabase() {
    abstract fun ChallengeDao(): ChallengeDao

    companion object{
        private var INSTANCE: ChallengeDB? = null

        fun getInstance(context: Context): ChallengeDB?{
            if (INSTANCE == null){
                synchronized(ChallengeDB::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ChallengeDB::class.java,"challenge.db")
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