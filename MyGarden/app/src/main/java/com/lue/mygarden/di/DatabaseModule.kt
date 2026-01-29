package com.lue.mygarden.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.lue.mygarden.data.local.AppDatabase
import com.lue.mygarden.data.local.dao.GardenPlantingDao
import com.lue.mygarden.data.local.dao.PlantDao
import com.lue.mygarden.data.repository.PlantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        Log.d("DatabaseModule", "AppDatabase instance created") // 👈
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sunflower-db"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun providePlantDao(database: AppDatabase): PlantDao {
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun provideGardenPlantingDao(database: AppDatabase): GardenPlantingDao {
        return database.gardenPlantingDao()
    }
}