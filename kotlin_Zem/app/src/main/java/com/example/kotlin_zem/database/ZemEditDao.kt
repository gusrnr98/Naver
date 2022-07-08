package com.example.kotlin_zem.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ZemEditDao {
    @Query("select * from zemedit")
    fun getALL(): List<ZemEdit>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(zemedit: ZemEdit)

    @Query("select  * from zemedit WHERE name = :username")
    fun selectUserById(username: Long): List<ZemEdit>

    @Query("DELETE from zemedit WHERE id = :userId")
    fun deleteUserById(userId: Long): Int

    @Query("UPDATE zemedit SET date = :date, dayofweek = :dayofweek, alram =:alram, zemcon=:zemcon WHERE id = :userId")
    fun allUpdateById(userId: Long, date: String, dayofweek: String,alram: String,zemcon: String): Int
}