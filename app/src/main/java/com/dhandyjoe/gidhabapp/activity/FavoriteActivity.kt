package com.dhandyjoe.gidhabapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.adapter.UserAdapter
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.database.SQLiteDatabase
import com.dhandyjoe.gidhabapp.databinding.ActivityFavoriteBinding
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private var db = SQLiteDatabase(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Favorite User"

        val getFavorite = db.getFavoriteMovie()
        Log.d("cek", getFavorite.toString())

        showRecycleView(getFavorite)

    }

    private fun showRecycleView(userList: ArrayList<User>) {
        binding.pbLoading.visibility = View.GONE
        binding.rvFavoriteUsers.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteUsers.hasFixedSize()
        val data = UserAdapter(userList)
        binding.rvFavoriteUsers.adapter = data
        binding.rvFavoriteUsers.visibility = View.VISIBLE

        data.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.NAME, data.login)
                startActivity(intent)
            }
        })
    }

}