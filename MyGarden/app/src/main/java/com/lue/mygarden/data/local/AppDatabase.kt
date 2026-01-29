package com.lue.mygarden.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lue.mygarden.data.local.dao.GardenPlantingDao
import com.lue.mygarden.data.model.Plant
import com.lue.mygarden.data.local.dao.PlantDao
import com.lue.mygarden.data.model.GardenPlanting

@Database(
    entities = [Plant::class, GardenPlanting::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun gardenPlantingDao(): GardenPlantingDao
}