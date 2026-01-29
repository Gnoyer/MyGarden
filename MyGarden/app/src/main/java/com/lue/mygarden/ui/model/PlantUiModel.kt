package com.lue.mygarden.ui.model

import androidx.room.PrimaryKey

data class PlantUiModel (
    val plantId: String,
    val name: String,
    val description: String,
    val growZoneNumber: Int = 0, //生长区域编号
    val imageUrl: String,
    val wateringInterval: Int, //需要浇水的间隔天数
)