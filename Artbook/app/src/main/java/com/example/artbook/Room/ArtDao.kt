package com.example.artbook.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArtDao {

    @Insert
    fun insert(art : ArtModel)

    @Query("select * from arts")
    fun getAll() : MutableList<ArtModel>

    @Query("select * from arts where nid =:nid")
    fun getById(nid : Int) : ArtModel

    @Delete
    fun delete(art : ArtModel)
}