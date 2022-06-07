package com.example.kotlin_challenge.database
import androidx.room.*

@Dao
interface ChallengeDao {
    @Query("select * from challenge")
    fun getAll(): List<Challenge>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(challenge: Challenge)


    @Query("DELETE from challenge WHERE id = :userId")
    fun deleteUserById(userId: Long): Int
}