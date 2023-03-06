package com.anushka.retrofitdemo


import com.google.gson.annotations.SerializedName

data class VenuesDataItem(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("position")
    val position: VenuePosition
)

data class VenuePosition(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double
)