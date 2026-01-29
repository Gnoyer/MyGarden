package com.lue.mygarden.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "garden_plantings",
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = ["plantId"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class GardenPlanting(
    @PrimaryKey(autoGenerate = true) val gardenPlantId: Int,
    val plantId: String,
    val plantingDate: Long,
    val lastWateredDate: Long,
)