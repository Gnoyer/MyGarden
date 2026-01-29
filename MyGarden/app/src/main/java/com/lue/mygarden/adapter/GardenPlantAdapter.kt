package com.lue.mygarden.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lue.mygarden.R
import com.lue.mygarden.data.model.GardenPlanting
import com.lue.mygarden.databinding.ItemGardenPlantBinding
import com.lue.mygarden.ui.model.PlantWithGardenInfo
import kotlinx.coroutines.currentCoroutineContext
import java.util.Calendar

class GardenPlantAdapter(
    private val onWaterClick: (GardenPlanting) -> Unit,
    private val onRemoveClick: (Int) -> Unit,
    private val onPlantClick: (String) -> Unit
) : ListAdapter<PlantWithGardenInfo, GardenPlantAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGardenPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onWaterClick, onRemoveClick)
    }

    inner class ViewHolder(
            private val binding: ItemGardenPlantBinding
        ) : RecyclerView.ViewHolder(binding.root) {
           fun bind(
               item: PlantWithGardenInfo,
               onWater: (GardenPlanting) -> Unit,
               onRemove: (Int) -> Unit
            ) {
                with(binding) {
                    plantName.text = item.plant.name

                    // 格式化种植日期
                    val plantedDate = formatDateString(item.plantingDate)
                    plantDate.text = "种植时间\n$plantedDate"

                    // 格式化最后浇水日期和间隔
                    val lastWateredDate = formatDateString(item.lastWateredDate)
                    wateringInfo.text = "浇水时间\n$lastWateredDate"

                    // 设置按钮点击事件
                    waterButton.setOnClickListener { onWater(GardenPlanting(item.gardenPlantId, item.plant.plantId, item.plantingDate, System.currentTimeMillis())) }
                    removeButton.setOnClickListener { onRemove(item.gardenPlantId) }
                    plantImage.setOnClickListener { onPlantClick(item.plant.plantId) }
                }
                loadPlantImage(binding.root.context, item.plant.imageUrl, binding.plantImage)
            }
        }

    private fun formatDateString(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$year.$month.$day,$hour:$minute:$second "
    }


    private fun loadPlantImage(context: Context, url: String, imageView: ImageView) {
        if (url.startsWith("local/")) {
            val name = url.substringAfter("local/")
            val resourceId = getDrawableResourceId(name)
            resourceId?.let {
                imageView.setImageResource(it)
            }
        } else {
//          Glide.with(imageView).load(url).into(imageView)
        }
    }

    private fun getDrawableResourceId(drawableName: String): Int? {
        return when (drawableName) {
            "rose" -> R.drawable.rose
            "fern" -> R.drawable.fern
            else -> null
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PlantWithGardenInfo>() {
        override fun areItemsTheSame(oldItem: PlantWithGardenInfo, newItem: PlantWithGardenInfo): Boolean =
            oldItem.plant.plantId == newItem.plant.plantId

        override fun areContentsTheSame(oldItem: PlantWithGardenInfo, newItem: PlantWithGardenInfo): Boolean =
            oldItem == newItem
    }
}