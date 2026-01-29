package com.lue.mygarden.ui.model

import androidx.room.Embedded
import com.lue.mygarden.data.model.Plant

data class PlantWithGardenInfo(
    @Embedded val plant: Plant,
    val plantingDate: Long,
    val lastWateredDate: Long,
    val gardenPlantId: Int,
) {
}