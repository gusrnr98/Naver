package com.example.kotlin_zem.database

import androidx.room.*

@Dao
interface ZemCompletionDao {
    @Query("select * from zemcompletion")
    fun getALL(): List<ZemCompletion>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(zemcompletion: ZemCompletion)

    @Query("select  * from zemcompletion WHERE id = :userId")
    fun selectUserById(userId: Long): List<ZemCompletion>

    @Query("DELETE from zemcompletion WHERE id = :userId")
    fun deleteUserById(userId: Long): Int

    @Query("UPDATE zemcompletion SET zemcheck=zemcheck+1 WHERE id = :userId")
    fun updateById(userId: Long): Int

    @Query("UPDATE zemcompletion SET habitname = :habitname, date = :date, dayofweek = :dayofweek, alram =:alram, zemcon=:zemcon,zemconinfo=:zemconinfo WHERE id = :userId")
    fun allUpdateById(userId: Long, habitname: String, date: String, dayofweek: String,alram: String,zemcon: String,zemconinfo: String): Int


}