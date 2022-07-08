package com.example.kotlin_zem.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zemedit")
data class ZemEdit(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") var name: Long,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "dayofweek") var dayofweek: String,
    @ColumnInfo(name = "alram") var alram: String,
    @ColumnInfo(name = "zemcon") var zemcon: String,
    @ColumnInfo(name = "zemconinfo") var zemconinfo: String

){
    constructor(): this(null,0,"","","","","")
}
