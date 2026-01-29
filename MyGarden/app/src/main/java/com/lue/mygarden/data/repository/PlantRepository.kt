package com.lue.mygarden.data.repository

import android.util.Log
import com.lue.mygarden.data.model.Plant
import com.lue.mygarden.data.local.dao.PlantDao
import com.lue.mygarden.data.remote.PlantService
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

@Singleton
class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
    private val plantService: PlantService
) {

fun getPlants(): Flow<List<Plant>> = flow {
    // 确保数据库操作在 IO 线程执行
    val cachedPlants = withContext(Dispatchers.IO) {
        plantDao.getPlants().first()
    }
    emit(cachedPlants)

    try {
        val remotePlants = plantService.getPlants()
        withContext(Dispatchers.IO) {
            plantDao.insertAll(remotePlants)
        }
        val updatedPlants = withContext(Dispatchers.IO) {
            plantDao.getPlants().first()
        }
        emit(updatedPlants)
    } catch (e: Exception) {
        Log.w("PlantRepository", "Failed to fetch plants from network", e)
    }
}


    fun getPlantById(plantId: String): Flow<Plant> = plantDao.getPlantById(plantId)
}