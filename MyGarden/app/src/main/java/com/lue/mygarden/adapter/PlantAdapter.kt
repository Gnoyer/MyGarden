package com.lue.mygarden.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lue.mygarden.R
import com.lue.mygarden.databinding.ItemPlantBinding
import com.lue.mygarden.ui.model.PlantUiModel

class PlantAdapter (
    private val onPlantClick: (String) -> Unit
) : ListAdapter<PlantUiModel, PlantAdapter.ViewHolder>(PlantDiffCallback()) {
    inner class ViewHolder(
        private val binding: ItemPlantBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plant: PlantUiModel) {
            binding.apply {
                textPlantName.text = plant.name
            }
            binding.imagePlant.setOnClickListener { onPlantClick(plant.plantId) }
            loadPlantImage(binding.root.context, plant.imageUrl, binding.imagePlant)
        }
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = getItem(position)
        holder.bind(plant)
    }

    class PlantDiffCallback : DiffUtil.ItemCallback<PlantUiModel>() {
        override fun areItemsTheSame(old: PlantUiModel, new: PlantUiModel) = old.plantId == new.plantId
        override fun areContentsTheSame(old: PlantUiModel, new: PlantUiModel) = old == new
    }
}