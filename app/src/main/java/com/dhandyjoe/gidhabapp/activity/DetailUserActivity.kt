package com.dhandyjoe.gidhabapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandyjoe.gidhabapp.R
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.databinding.ActivityDetailUserBinding
import com.dhandyjoe.gidhabapp.model.DetailUser
import com.dhandyjoe.gidhabapp.adapter.SectionPagerAdapter
import com.dhandyjoe.gidhabapp.database.SQLiteDatabase
import com.dhandyjoe.gidhabapp.model.User
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
    private var data: DetailUser? = null
    private var statusFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetails.title = "Details User"
        binding.ivFavorite.visibility = View.GONE
        val username = intent.getStringExtra(NAME)

        Log.d("tesUsernameDetail", username!!)

        showDetailUsers(username)

        Log.d("favorite", data?.id.toString())
        showFollowersIndicator(username)
        showFollowingIndicator(username)

        binding.ivFavorite.setOnClickListener {
            addDeleteMovie()
        }


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
                data = response.body()
                statusFavorite()
                Glide.with(this@DetailUserActivity)
                    .load(data?.avatar_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.imageDetailUser)
                binding.tvName.text = data?.name ?: ""
                binding.tvUsername.text = data?.login ?: ""
                binding.ivFavorite.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Toast.makeText(this@DetailUserActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showFollowersIndicator(query: String) {
        RetrofitClient.apiInstance.getListFollowers(query)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    val data = response.body()
                    binding.tvFollowers.text = StringBuilder(data?.size.toString()).append(" followers")
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Toast.makeText(this@DetailUserActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showFollowingIndicator(query: String) {
        RetrofitClient.apiInstance.getListFollowing(query)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    val data = response.body()
                    binding.tvFollowers.text = StringBuilder(data?.size.toString()).append(" following")
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Toast.makeText(this@DetailUserActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun addDeleteMovie() {
        if(statusFav) {
            val db = SQLiteDatabase(this)
            db.deleteFavorite(data!!)
            iconFavorite(false)
        } else {
            val dataHappyPLace = DetailUser(
                data?.id ?: 0,
                data?.login ?: "",
                data?.name ?: "",
                data?.avatar_url ?: ""
            )
            val db = SQLiteDatabase(this)
            db.addFavorite(dataHappyPLace)
            iconFavorite(true)
        }
    }

    fun iconFavorite(boolean: Boolean) {
        if(boolean) {
            statusFav = true
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite)
        } else {
            statusFav = false
            binding.ivFavorite.setImageResource(R.drawable.ic_unfavorite)
        }
    }

    fun statusFavorite () {
        val dbHandler = SQLiteDatabase(this)
        val cursor = dbHandler.queryById(data?.id.toString())
        Log.d("favorite", data?.id.toString())

        if(cursor.moveToNext()) {
            iconFavorite(true)
        } else {
            iconFavorite(false)
        }
    }
}