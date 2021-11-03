package com.dhandyjoe.gidhabapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.dhandyjoe.gidhabapp.DETAIL_USER
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.databinding.ActivityDetailUserBinding
import com.dhandyjoe.gidhabapp.model.DetailUser
import com.dhandyjoe.gidhabapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {
    companion object {
        const val NAME = "name"
    }

    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetails.title = "Details User"
        val username = intent.getStringExtra(NAME)

        showDetailUsers(username!!)

    }

    private fun showDetailUsers(username: String) {
        RetrofitClient.apiInstance.getDetailUser(username).enqueue(object : Callback<DetailUser>{
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                val data = response.body()
                binding.tvName.text = data?.name ?: ""
                binding.tvUsername.text = data?.login ?: ""
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}