package com.dhandyjoe.gidhabapp.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dhandyjoe.gidhabapp.fragment.FollowersFragment
import com.dhandyjoe.gidhabapp.fragment.FollowingFragment

class SectionPagerAdapter(activity: AppCompatActivity, private val data: String): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val followerFragment = FollowersFragment()
        val followingFragment = FollowingFragment()

        var fragment: Fragment? = null
        val bundle = Bundle()
        bundle.putString("username", data)

        followerFragment.arguments = bundle
        followingFragment.arguments = bundle

        when (position) {
            0 -> fragment = followerFragment
            1 -> fragment = followingFragment
        }
        return fragment as Fragment
    }
}