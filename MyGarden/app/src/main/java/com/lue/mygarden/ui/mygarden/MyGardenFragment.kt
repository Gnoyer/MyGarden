package com.lue.mygarden.ui.mygarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lue.mygarden.R
import com.lue.mygarden.adapter.GardenPlantAdapter
import com.lue.mygarden.databinding.FragmentMyGardenBinding
import com.lue.mygarden.databinding.FragmentPlantDetailBinding
import com.lue.mygarden.ui.viewpager.ViewPagerFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.jvm.java

@AndroidEntryPoint
class MyGardenFragment : Fragment() {

    private val viewModel: MyGardenViewModel by viewModels()
    private var _binding: FragmentMyGardenBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GardenPlantAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyGardenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = GardenPlantAdapter(
            onWaterClick = { plant ->
                viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.updateGardenPlanting(plant)
                }
            },
            onRemoveClick = { plantId ->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.removeGardenPlanting(plantId)
                }
            },
            onPlantClick = { plantId ->
                findNavController().navigate(
                    ViewPagerFragmentDirections.actionFragmentViewPagerToFragmentPlantDetail(plantId)
                )
            }
        )

        setupRecyclerView(adapter)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.gardenPlantings.collect { gardenPlantings ->
                    adapter.submitList(gardenPlantings)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isEmpty.collect { isEmpty ->
                    binding.emptyView1.isVisible = isEmpty
                    binding.recyclerView.isVisible = !isEmpty
                }
            }
        }
    }

    private fun setupRecyclerView(adapter: GardenPlantAdapter) {
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            this.adapter = adapter
        }
    }
}