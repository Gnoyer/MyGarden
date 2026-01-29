package com.lue.mygarden.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lue.mygarden.ui.mygarden.MyGardenFragment
import com.lue.mygarden.ui.plantlist.PlantListFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> MyGardenFragment()  // 需要创建对应页面的 Fragment
            1 -> PlantListFragment()  // 需要创建对应页面的 Fragment
            else -> MyGardenFragment()
        }
    }
}
