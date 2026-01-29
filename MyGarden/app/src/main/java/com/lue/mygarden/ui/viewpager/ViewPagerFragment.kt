package com.lue.mygarden.ui.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.lue.mygarden.R
import com.lue.mygarden.adapter.ViewPagerAdapter
import com.lue.mygarden.databinding.FragmentPlantDetailBinding
import com.lue.mygarden.databinding.FragmentViewPagerBinding
import com.lue.mygarden.ui.plantdetail.PlantDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewPagerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val viewPager = binding.viewPager
        val tabs = binding.tabs

        // 设置适配器
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // 使用 TabLayoutMediator 关联 TabLayout 和 ViewPager2
       TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "花园"
                1 -> tab.text = "列表"
                else -> tab.text = "花园"
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}