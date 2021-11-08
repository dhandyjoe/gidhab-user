package com.dhandyjoe.gidhabapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

import com.dhandyjoe.gidhabapp.R
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.databinding.ActivityDetailUserBinding
import com.dhandyjoe.gidhabapp.model.DetailUser
import com.dhandyjoe.gidhabapp.adapter.SectionPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val NAME = "name"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetails.title = "Details User"
        val username = intent.getStringExtra(NAME)

        Log.d("tesUsernameDetail", username!!)

        showDetailUsers(username)

        val sectionsPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    private fun showDetailUsers(username: String) {
        RetrofitClient.apiInstance.getDetailUser(username).enqueue(object : Callback<DetailUser>{
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                val data = response.body()
                Glide.with(this@DetailUserActivity)
                    .load(data?.avatar_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageDetailUser)
                binding.tvName.text = data?.name ?: ""
                binding.tvUsername.text = data?.login ?: ""
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}