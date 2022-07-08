package com.example.kotlin_zem.DataClass

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Zemdata(
    var id: Long?,
    var habitimage: String,
    var habittitle: String,
    var habitname: String,
    var date: String,
    var dayofweek: String,
    var alram: String,
    var zemcon: String,
    var zemconinfo: String
)
