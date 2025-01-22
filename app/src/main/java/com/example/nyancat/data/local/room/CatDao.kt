package com.example.nyancat.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nyancat.data.local.entity.CatEntity

@Dao
interface CatDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCats(cats: List<CatEntity>)

    @Query("SELECT * FROM cat")
    suspend fun getAllCatsDirect(): List<CatEntity>
}