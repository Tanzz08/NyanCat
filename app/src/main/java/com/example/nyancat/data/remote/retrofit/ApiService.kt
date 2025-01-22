package com.example.nyancat.data.remote.retrofit

import com.example.nyancat.data.remote.response.TheCatApiResponse
import com.example.nyancat.data.remote.response.TheCatApiResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("images/search?limit=4&breed_ids=aege")
    suspend fun getData(@Query("api_key") apiKey: String): List<TheCatApiResponseItem>

    @GET("images/search?limit=4&breed_ids=beng")
    suspend fun bengCats(@Query("api_key") apiKey: String): List<TheCatApiResponseItem>

    @GET("images/search?limit=4&breed_ids=asho")
    suspend fun ashoCats(@Query("api_key") apiKey: String): List<TheCatApiResponseItem>
}