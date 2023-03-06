package com.anushka.retrofitdemo


import com.google.gson.annotations.SerializedName

data class SessionDataItem(
    @SerializedName("endTimeUtc")
    val endTimeUtc: String,
    @SerializedName("path")
    val path: List<Path>,
    @SerializedName("sessionId")
    val sessionId: String,
    @SerializedName("startTimeLocal")
    val startTimeLocal: String,
    @SerializedName("startTimeUtc")
    val startTimeUtc: String,
    @SerializedName("userId")
    val userId: String
)

data class Path(
    @SerializedName("position")
    val position: Position,
    @SerializedName("userTimeUtc")
    val userTimeUtc: String
)

data class Position(
    @SerializedName("x")
    val x: String,
    @SerializedName("y")
    val y: String
)