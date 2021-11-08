package com.dhandyjoe.gidhabapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        binding.btn.setOnClickListener {
            showUsers(binding.svUsers.text.toString())
        }
    }

    private fun showRecycleView(userList: ArrayList<User>) {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
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
                        showRecycleView(data!!)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("Failed", "Gagal")
                }
            })
    }
}