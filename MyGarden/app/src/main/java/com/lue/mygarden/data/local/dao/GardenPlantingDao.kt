package com.lue.mygarden.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.lue.mygarden.data.model.GardenPlanting
import com.lue.mygarden.ui.model.PlantWithGardenInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenPlantingDao {

    @Transaction
    @Query("SELECT p.*, g.plantingDate, g.lastWateredDate, g.gardenPlantId " +
           "FROM plants p " +
           "INNER JOIN garden_plantings g ON p.plantId = g.plantId " +
           "ORDER BY g.plantingDate DESC")
    fun getPlantedWithGardenInfo(): Flow<List<PlantWithGardenInfo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGardenPlanting(gardenPlanting: GardenPlanting)

    @Query("DELETE FROM garden_plantings WHERE gardenPlantId = :plantId")
    suspend fun removeGardenPlanting(plantId: Int)

    @Update
    suspend fun updateGardenPlanting(gardenPlanting: GardenPlanting)
}