package com.dhandyjoe.gidhabapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.activity.DetailUserActivity
import com.dhandyjoe.gidhabapp.adapter.UserAdapter
import com.dhandyjoe.gidhabapp.api.RetrofitClient
import com.dhandyjoe.gidhabapp.databinding.FragmentFollowersBinding
import com.dhandyjoe.gidhabapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowersFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFollowersBinding

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

        binding = FragmentFollowersBinding.inflate(inflater, container, false)

        Log.d("tesUsername", args!!)
        showUsers(args)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        const val USERNAME = "username"
    }

    private fun showRecycleView(userList: ArrayList<User>) {
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        val data = UserAdapter(userList)
        binding.rvFollowers.adapter = data
    }

    private fun showUsers(query: String) {
        RetrofitClient.apiInstance.getListFollowers(query)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    val data = response.body()
                    binding.pbLoading.visibility = View.GONE
                    binding.rvFollowers.visibility = View.VISIBLE
                    showRecycleView(data!!)
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Toast.makeText(activity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}