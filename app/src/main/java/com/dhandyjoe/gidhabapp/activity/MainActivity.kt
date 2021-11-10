package com.dhandyjoe.gidhabapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.adapter.UserAdapter
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.databinding.ActivityMainBinding
import com.dhandyjoe.gidhabapp.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Gidhab User"
        binding.ivFavorite.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.btn.setOnClickListener {
            binding.tvStatusData.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
            showUsers(binding.svUsers.text.toString())
        }
    }

    private fun showRecycleView(userList: ArrayList<User>) {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.hasFixedSize()
        val data = UserAdapter(userList)
        binding.rvUser.adapter = data

        data.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.NAME, data.login)
                startActivity(intent)
            }
        })
    }

    private fun showUsers(query: String) {
        RetrofitClient.apiInstance.getUser(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()?.items
                        Log.d("testData", data.toString())
                        binding.pbLoading.visibility = View.GONE
                        binding.rvUser.visibility = View.VISIBLE
                        showRecycleView(data!!)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}