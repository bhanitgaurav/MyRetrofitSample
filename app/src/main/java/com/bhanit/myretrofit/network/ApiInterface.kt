package com.bhanit.myretrofit.network

import com.bhanit.myretrofit.data.DataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("/api/users?page=2")
    suspend fun getAllUsers(): Any
}