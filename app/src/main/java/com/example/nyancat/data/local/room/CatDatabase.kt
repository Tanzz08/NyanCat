package com.example.nyancat.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.nyancat.data.local.entity.CatEntity

@Database(entities = [CatEntity::class], version = 1, exportSchema = false)
abstract class CatDatabase : RoomDatabase() {
    abstract fun catDao(): CatDao

    companion object {
        @Volatile
        private var instance: CatDatabase? = null
        fun getInstance(context: Context): CatDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    CatDatabase::class.java, "Cat.db"
                ).build()
            }
    }
}