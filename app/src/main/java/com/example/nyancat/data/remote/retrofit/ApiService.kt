package com.example.nyancat.data.remote.retrofit

import com.example.nyancat.data.remote.response.TheCatApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("images/search?limit=10&breed_ids=beng")
    suspend fun getData(@Query("api_key") apiKey: String): TheCatApiResponse
}