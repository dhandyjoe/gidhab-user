package com.dhandyjoe.gidhabapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.R
import com.dhandyjoe.gidhabapp.activity.DetailUserActivity
import com.dhandyjoe.gidhabapp.activity.MainActivity
import com.dhandyjoe.gidhabapp.adapter.UserAdapter
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.databinding.FragmentFollowingBinding
import com.dhandyjoe.gidhabapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments?.getString(USERNAME)
        binding = FragmentFollowingBinding.inflate(inflater, container,false)


        Log.d("tesUsername", "ini di following = ${args!!}")
        showUsers(args)

        return binding.root
    }

    companion object {
        const val USERNAME = "username"
    }

    private fun showRecycleView(userList: ArrayList<User>) {
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        val data = UserAdapter(userList)
        binding.rvFollowing.adapter = data
    }

    private fun showUsers(query: String) {
        RetrofitClient.apiInstance.getListFollowing(query)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    val data = response.body()
                    binding.pbLoading.visibility = View.GONE
                    binding.rvFollowing.visibility = View.VISIBLE
                    showRecycleView(data!!)
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Toast.makeText(activity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}