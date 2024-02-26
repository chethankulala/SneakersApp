package com.example.sneakersapp.features.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sneakersapp.features.model.Media
import com.example.sneakersapp.features.view.SneakerImageFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val media: Media): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SneakerImageFragment.newInstance(media, position)
            1 -> SneakerImageFragment.newInstance(media, position)
            2 -> SneakerImageFragment.newInstance(media, position)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}