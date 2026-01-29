package com.lue.mygarden.data.remote

import com.lue.mygarden.data.model.Plant
import retrofit2.http.GET

interface PlantService {
    @GET("plant.json") // 你的 GitHub Pages 路径
    suspend fun getPlants(): List<Plant>
}