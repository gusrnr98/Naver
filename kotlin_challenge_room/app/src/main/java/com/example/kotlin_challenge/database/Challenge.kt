package com.example.kotlin_challenge.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "challenge")
data class Challenge(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "challengeTittle") var challengeTittle: String,
    @ColumnInfo(name = "challengeMenu") var challengeMenu: String
){
    constructor(): this(null,"","","")
}
