package com.lue.mygarden.ui.plantlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lue.mygarden.data.repository.PlantRepository
import com.lue.mygarden.ui.model.PlantUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class PlantListViewModel@Inject constructor( // 👈 通过构造函数注入
    private val repository: PlantRepository
) : ViewModel() {

    // 👇 使用 StateFlow（现代推荐）暴露不可变数据流
    val plantList = repository.getPlants().map { plants ->
        plants.map { plant ->
            PlantUiModel(
                plantId = plant.plantId,
                name = plant.name,
                description = plant.description,
                growZoneNumber = plant.growZoneNumber,
                imageUrl = plant.imageUrl,
                wateringInterval = plant.wateringInterval
            )
        }
    }
}