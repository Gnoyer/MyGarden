package com.lue.mygarden.ui.mygarden

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lue.mygarden.data.model.GardenPlanting
import com.lue.mygarden.data.repository.GardenPlantingRepository
import com.lue.mygarden.ui.model.PlantWithGardenInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MyGardenViewModel @Inject constructor(
    private val repository: GardenPlantingRepository
) : ViewModel() {
    val gardenPlantings: StateFlow<List<PlantWithGardenInfo>> =
        repository.getPlantedWithGardenInfo().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val isEmpty: StateFlow<Boolean> =
        gardenPlantings.map { it.isEmpty() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true
            )


    suspend fun removeGardenPlanting(plantId: Int) {
        repository.removeGardenPlanting(plantId)
    }

    suspend fun updateGardenPlanting(plant: GardenPlanting) {
        repository.updateGardenPlanting(plant)
    }
}