package com.anushka.retrofitdemo

import retrofit2.Response
import retrofit2.http.GET

interface DwellService {


    @GET("/mr8nu9ue")
    suspend fun getSession():Response<SessionData>

    @GET("/2xwsu5y5")
    suspend fun getVenue():Response<VenuesData>


}