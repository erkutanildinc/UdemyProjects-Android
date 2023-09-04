package com.example.countriesapp.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name")
    var countryName: String?,
    @SerializedName("capital")
    var countryCapital: String?,
    @SerializedName("region")
    var countryRegion: String?,
    @SerializedName("language")
    var countryLanguage: String?,
    @SerializedName("currency")
    var countryCurrency: String?,
    @SerializedName("flag")
    var countryFlagUrl: String?
) {

}