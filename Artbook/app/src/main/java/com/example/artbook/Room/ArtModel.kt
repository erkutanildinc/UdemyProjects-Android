package com.example.artbook.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class ArtModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "nid")
    val nid: Int?,

    val artName: String,
    val artArtist: String,
    val artYear: String,
    val artImage: ByteArray
)
