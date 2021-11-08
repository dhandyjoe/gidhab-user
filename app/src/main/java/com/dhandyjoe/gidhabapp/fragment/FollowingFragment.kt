package com.dhandyjoe.gidhabapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var username: String

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
        val args = arguments?.getString("username")
        binding = FragmentFollowingBinding.inflate(inflater, container,false)


        Log.d("tesUsername", "ini di following = ${args!!}")
        showUsers(args)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
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
                    showRecycleView(data!!)
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}