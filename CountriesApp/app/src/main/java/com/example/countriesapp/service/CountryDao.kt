package com.example.countriesapp.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.example.countriesapp.model.Country

@Dao
interface CountryDao {

    //Data Access Object

    @Insert
    fun insertAll(vararg countries: Country) : List<Long>

    //Insert -> INSERT INTO
    // suspend -> coroutine, pause & resume
    // vararg -> multiple country objects
    // List<Long> -> primary keys
    @Query("SELECT * FROM country")
    fun getAllCountries() : List<Country>

    @Query("SELECT * FROM country WHERE uuid = :countryId")
    fun getCountry(countryId : Int) : Country

    @Query("DELETE FROM country")
    fun deleteAllCountries()


}