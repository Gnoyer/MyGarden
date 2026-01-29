package com.lue.mygarden.data.local

import com.lue.mygarden.data.local.dao.PlantDao
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.first

@Singleton
class DataInitializer @Inject constructor(
    private val plantDao: PlantDao
) {
    suspend fun initializePlants() {
        if (plantDao.getPlants().first().isEmpty()) {
            plantDao.insertAll(PlantDao.DEFAULT_PLANTS)
        }
    }
}
