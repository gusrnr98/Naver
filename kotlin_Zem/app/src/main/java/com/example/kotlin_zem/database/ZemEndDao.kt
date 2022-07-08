package com.example.kotlin_zem.database

import androidx.room.*

@Dao
interface ZemEndDao {
    @Query("select * from zemend")
    fun getALL(): List<ZemEnd>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(zemend: ZemEnd)

    @Query("select  * from zemend WHERE id = :userId")
    fun selectUserById(userId: Long): List<ZemEnd>

    @Query("DELETE from zemend WHERE id = :userId")
    fun deleteUserById(userId: Long): Int

    @Query("select sum(zemcon) from zemend")
    fun sumZemcon(): Int

    @Query("UPDATE zemend SET zemcheck=zemcheck+1 WHERE id = :userId")
    fun updateById(userId: Long): Int
}