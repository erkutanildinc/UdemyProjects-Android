package com.example.artbooktesting.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("arts")
data class Art(
    var name : String,
    var artistName : String,
    var year : Int,
    var imgUrl : String,

    @PrimaryKey(autoGenerate = true)
    var id : Int? = null
)
