package com.example.nyancat.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.nyancat.data.local.room.CatDao
import com.example.nyancat.data.remote.retrofit.ApiService
import com.example.nyancat.ui.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.nyancat.BuildConfig
import com.example.nyancat.data.local.entity.CatEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn


class CatRepository(
    private val apiService: ApiService,
    private val catDao: CatDao
) {
    fun getCat(): Flow<Result<List<CatEntity>>> = flow {
        emit(Result.Loading)
        try {
            // Mengambil response yang sekarang berupa List<TheCatApiResponseItem>
            val response = apiService.getData(BuildConfig.API_KEY)

            // Mapping dari TheCatApiResponseItem ke CatEntity
            val catList = response.map { cat ->
                // Ambil data dari breed pertama jika ada
                val breed = cat.breeds.firstOrNull()
                CatEntity(
                    id = cat.id,
                    name = breed?.name ?: "Unknown", // Nama breed kucing
                    description = breed?.description ?: "No description available", // Deskripsi breed
                    imageUrl = cat.url, // URL gambar kucing
                    url = breed?.wikipediaUrl // URL tambahan
                )
            }

            // Simpan ke database
            catDao.insertCats(catList)

            // Ambil data dari database
            val storedCats = catDao.getAllCatsDirect()
            emit(Result.Success(storedCats))
        } catch (e: Exception) {
            Log.e("CatRepository", "Error fetching cats", e)
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getBengCats(): Flow<Result<List<CatEntity>>> = flow {
        emit(Result.Loading)
        try {
            // Mengambil response yang sekarang berupa List<TheCatApiResponseItem>
            val response = apiService.bengCats(BuildConfig.API_KEY)

            // Mapping dari TheCatApiResponseItem ke CatEntity
            val catList = response.map { cat ->
                // Ambil data dari breed pertama jika ada
                val breed = cat.breeds.firstOrNull()
                CatEntity(
                    id = cat.id,
                    name = breed?.name ?: "Unknown", // Nama breed kucing
                    description = breed?.description ?: "No description available", // Deskripsi breed
                    imageUrl = cat.url, // URL gambar kucing
                    url = breed?.wikipediaUrl // URL tambahan
                )
            }

            // Simpan ke database
            catDao.insertCats(catList)

            // Ambil data dari database
            val storedCats = catDao.getAllCatsDirect()
            emit(Result.Success(storedCats))
        } catch (e: Exception) {
            Log.e("CatRepository", "Error fetching cats", e)
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getAshoCats(): Flow<Result<List<CatEntity>>> = flow {
        emit(Result.Loading)
        try {
            // Mengambil response yang sekarang berupa List<TheCatApiResponseItem>
            val response = apiService.ashoCats(BuildConfig.API_KEY)

            // Mapping dari TheCatApiResponseItem ke CatEntity
            val catList = response.map { cat ->
                // Ambil data dari breed pertama jika ada
                val breed = cat.breeds.firstOrNull()
                CatEntity(
                    id = cat.id,
                    name = breed?.name ?: "Unknown", // Nama breed kucing
                    description = breed?.description ?: "No description available", // Deskripsi breed
                    imageUrl = cat.url, // URL gambar kucing
                    url = breed?.wikipediaUrl // URL tambahan
                )
            }

            // Simpan ke database
            catDao.insertCats(catList)

            // Ambil data dari database
            val storedCats = catDao.getAllCatsDirect()
            emit(Result.Success(storedCats))
        } catch (e: Exception) {
            Log.e("CatRepository", "Error fetching cats", e)
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)



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

