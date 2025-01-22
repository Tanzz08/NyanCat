package com.example.nyancat.data.di

import android.content.Context
import com.example.nyancat.data.CatRepository
import com.example.nyancat.data.local.room.CatDatabase
import com.example.nyancat.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): CatRepository {
        val apiService = ApiConfig.getApiService()
        val database = CatDatabase.getInstance(context)
        val dao = database.catDao()
        return CatRepository.getInstance(apiService, dao)
    }
}