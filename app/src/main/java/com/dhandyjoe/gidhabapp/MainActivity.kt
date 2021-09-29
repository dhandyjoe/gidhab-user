package com.dhandyjoe.gidhabapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandyjoe.gidhabapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonObject
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

        // read JSON file using GSOn
        val jsonFile = getJsonDataFromAsset(this, "githubuser.json")
        val gson = Gson()
        val user: User = gson.fromJson(jsonFile, User::class.java)

        showRecycleView()
    }

    private fun showRecycleView() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val data = UserAdapter(userList, this)
        binding.rvUser.adapter = data
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }
}