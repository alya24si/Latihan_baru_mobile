package com.example.alya_love.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TenthTabsAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> KejadianBencanaFragment()
            1 -> PoskoBencanaFragment()
            2 -> DonasiBencanaFragment()
            3 -> LogistikFragment()
            4 -> DistribusiFragment()
            else -> throw IllegalStateException("Posisi tidak valid")
        }
    }
}