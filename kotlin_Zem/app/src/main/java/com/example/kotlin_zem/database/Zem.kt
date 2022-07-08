package com.example.kotlin_zem.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zem")
data class Zem(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "habitimage") var habitimage: String,
    @ColumnInfo(name = "habittitle") var habittitle: String,
    @ColumnInfo(name = "habitname") var habitname: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "dayofweek") var dayofweek: String,
    @ColumnInfo(name = "alram") var alram: String,
    @ColumnInfo(name = "zemcon") var zemcon: String,
    @ColumnInfo(name = "zemconinfo") var zemconinfo: String,
    @ColumnInfo(name = "zemeditcheck") var zemeditcheck: Int

){
    constructor(): this(null,"","","","","","","","",0)
}
