package com.lue.mygarden.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lue.mygarden.data.model.Plant
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the Plant class.
 */
@Dao
interface PlantDao {
    companion object {
        val DEFAULT_PLANTS = listOf(
            Plant("fern", "蕨类植物", "...", 1, "local/fern", 3),
            Plant("rose", "玫瑰", "...", 2, "local/rose", 2)
        )
    }
    @Query("SELECT * FROM plants ORDER BY name")
    fun getPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE plantId = :plantId")
    fun getPlantById(plantId: String): Flow<Plant>

    @Query("SELECT * FROM plants WHERE growZoneNumber = :growZoneNumber ORDER BY name")
     fun getPlantsWithGrowZoneNumber(growZoneNumber: Int): Flow<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertAll(plants: List<Plant>)
}