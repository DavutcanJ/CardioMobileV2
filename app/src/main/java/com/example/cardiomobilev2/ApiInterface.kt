package com.example.cardiomobilev2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @GET("{userId}")
    fun getData(@Path("userId") userId: String): Call<List<User>>

    @POST("getdeseaserate")
    fun getDeseaseData(@Body userData: User): Call<RateResponse>}