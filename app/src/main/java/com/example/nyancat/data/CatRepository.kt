package com.example.nyancat.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.nyancat.data.local.room.CatDao
import com.example.nyancat.data.remote.response.TheCatApiResponse
import com.example.nyancat.data.remote.response.TheCatApiResponseItem
import com.example.nyancat.data.remote.retrofit.ApiService
import com.example.nyancat.ui.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import com.example.nyancat.BuildConfig
import com.example.nyancat.data.local.entity.CatEntity


class CatRepository(
    private val apiService: ApiService,
    private val catDao: CatDao
) {
    fun getCat(): LiveData<Result<List<CatEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getData(BuildConfig.API_KEY)
            val cats = response.theCatApiResponse
            val catList = cats.map { cat ->
                CatEntity(
                    id = cat.id,
                    name = cat.breeds.firstOrNull()?.name ?: "Unknown",
                    description = cat.breeds.firstOrNull()?.description ?: "No description available",
                    imageUrl = cat.url,
                    url = cat.breeds.firstOrNull()?.vetstreetUrl ?: ""
                )
            }
            catDao.insertCats(catList) // Simpan ke database
            val storedCats = catDao.getAllCatsDirect() // Ambil data dari database
            emit(Result.Success(storedCats))
        } catch (e: Exception) {
            Log.e("CatRepository", "Error fetching cats", e)
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: CatRepository? = null
        fun getInstance(
            apiService: ApiService,
            catDao: CatDao
        ): CatRepository =
            instance ?: synchronized(this) {
                instance ?: CatRepository(apiService, catDao)
            }.also { instance = it }
    }
}
