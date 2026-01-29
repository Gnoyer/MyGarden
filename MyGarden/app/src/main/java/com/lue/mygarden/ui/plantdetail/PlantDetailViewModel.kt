package com.lue.mygarden.ui.plantdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lue.mygarden.data.model.Plant
import com.lue.mygarden.data.repository.GardenPlantingRepository
import com.lue.mygarden.data.repository.PlantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: PlantRepository,
    private val gardenPlantRepository: GardenPlantingRepository
) : ViewModel() {

    private val plantId: String = checkNotNull(savedStateHandle["plantId"]) {
        "plantId must be provided"
    }

    // 👇 通过 ID 查询植物（实时 Flow）
    val plant = repository.getPlantById(plantId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    suspend fun addPlantToGarden(plant: Plant) {
        // 调用 Repository 将植物添加到花园表
        gardenPlantRepository.insertGardenPlanting(plant.plantId)
    }
}