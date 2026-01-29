package com.lue.mygarden.data.repository

import android.util.Log
import com.lue.mygarden.data.local.dao.GardenPlantingDao
import com.lue.mygarden.data.local.dao.PlantDao
import com.lue.mygarden.data.model.GardenPlanting
import com.lue.mygarden.data.model.Plant
import com.lue.mygarden.ui.model.PlantWithGardenInfo
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow

@Singleton
class GardenPlantingRepository @Inject constructor(
    private val dao: GardenPlantingDao
) {
    fun getPlantedWithGardenInfo(): Flow<List<PlantWithGardenInfo>> {
        return dao.getPlantedWithGardenInfo()
    }

    suspend fun insertGardenPlanting(plantId : String) {
        val gardenPlant = GardenPlanting(
            plantId = plantId,
            gardenPlantId = 0,  // ID 会在数据库插入时自动生成
            plantingDate = System.currentTimeMillis(),
            lastWateredDate = System.currentTimeMillis()
        )
        Log.d("GardenPlantingRepository", "Inserting gardenPlant: $gardenPlant")
        dao.insertGardenPlanting(gardenPlant)
    }

    suspend fun removeGardenPlanting(plantId: Int) {
        dao.removeGardenPlanting(plantId)
    }

    suspend fun updateGardenPlanting(gardenPlanting: GardenPlanting) {
        dao.updateGardenPlanting(gardenPlanting)
    }
}