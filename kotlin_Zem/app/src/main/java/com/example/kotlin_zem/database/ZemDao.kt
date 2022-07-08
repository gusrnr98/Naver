package com.example.kotlin_zem.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ZemDao {
    @Query("select * from zem")
    fun getALL(): List<Zem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(zem: Zem)

    @Query("select  * from zem WHERE id = :userId")
    fun selectUserById(userId: Long): List<Zem>

    @Query("DELETE from zem WHERE id = :userId")
    fun deleteUserById(userId: Long): Int
}