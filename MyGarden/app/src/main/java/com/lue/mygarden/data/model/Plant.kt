package com.lue.mygarden.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey  val plantId: String,
    val name: String,
    val description: String,
    val growZoneNumber: Int = 0, //生长区域编号
    val imageUrl: String,
    val wateringInterval: Int, //需要浇水的间隔天数
)
