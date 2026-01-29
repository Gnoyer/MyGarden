package com.lue.mygarden.ui.plantdetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.lue.mygarden.R
import com.lue.mygarden.data.model.Plant
import com.lue.mygarden.databinding.FragmentPlantDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlantDetailFragment : Fragment() {

    private var _binding: FragmentPlantDetailBinding? = null
    private val binding get() = _binding!!

    // 👇 通过 SavedStateHandle 获取参数
    private val viewModel: PlantDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 绑定返回按钮功能
        binding.buttonBack.setOnClickListener {
            handleBackButton()
        }

        // 绑定收藏/分享按钮功能
        binding.buttonFavorite.setOnClickListener {
            val currentPlant = viewModel.plant.value
            currentPlant?.let { plant ->
                viewLifecycleOwner.lifecycleScope.launch {
                    try {
                        Log.d("PlantRepository", "Inserting plant: ${plant.plantId}")
                        viewModel.addPlantToGarden(plant)
                        Log.d("PlantRepository", "Plant inserted")
                        // 显示成功通知
                        showToast("添加成功: ${plant.name}")
                    } catch (e: Exception) {
                        // 显示错误通知
                        showToast("添加失败: ${e.message}")
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.plant.collect { plant ->
                    bindPlant(plant)
                }
            }
        }
    }

    private fun handleBackButton() {
        findNavController().navigateUp()
    }

    private fun bindPlant(plant: Plant?) {
        plant ?: return
        binding.apply {
            textViewName.text = plant.name
            textViewWatering.text = "💧 浇水提醒：${plant.wateringInterval} 天/次"
            textViewDescription.text = plant.description.replace("\\n", "\n")
        }
        loadPlantImage(requireContext(), plant.imageUrl, binding.imageViewPlant)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}