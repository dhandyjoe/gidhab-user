package com.dhandyjoe.gidhabapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.text.Charsets.UTF_8

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.toolbar.title = "Gidhab User's"

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