package com.dhandyjoe.gidhabapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.DETAIL_USER
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.adapter.UserAdapter
import com.dhandyjoe.gidhabapp.databinding.ActivityMainBinding
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "Gidhab User"

        userList = ArrayList()

        try {
            val obj = JSONObject(getJsonDataFromAsset()!!)
            val userArray = obj.getJSONArray("users")

            for (i in 0 until userArray.length()) {
                val user = userArray.getJSONObject(i)

                val username = user.getString("username")
                val name = user.getString("name")
                val avatar = user.getString("avatar")
                val company = user.getString("company")
                val location = user.getString("location")
                val repository = user.getInt("repository")
                val follower = user.getInt("follower")
                val following = user.getInt("following")

                val userDetails = User(username, name, avatar, company, location, repository, follower, following)

                userList.add(userDetails)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        showRecycleView()
    }

    private fun showRecycleView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val data = UserAdapter(userList, this)
        binding.rvUser.adapter = data

        data.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(DETAIL_USER, data)
                startActivity(intent)
            }
        })
    }

    private fun getJsonDataFromAsset(): String? {
        var jsonString: String? = null
        val charset = Charsets.UTF_8
        try {
            val myUserJSonFile = assets.open("githubuser.json")
            val size = myUserJSonFile.available()
            val buffer = ByteArray(size)
            myUserJSonFile.read(buffer)
            myUserJSonFile.close()
            jsonString = String(buffer, charset)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}