package com.dhandyjoe.gidhabapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dhandyjoe.gidhabapp.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarDetails.title = "Details User"

        val user = intent.getParcelableExtra<User>(DETAIL_USER)
        Glide.with(this)
            .load(getImage(user!!.avatar))
            .circleCrop()
            .into(binding.ivDetail)

        binding.tvUsernameDetail.text = user.username
        binding.tvCompanyDetail.text = user.company
        binding.tvFollower.text = user.follower.toString()
        binding.tvRepo.text = user.repository.toString()
        binding.tvFollowing.text = user.following.toString()
        binding.tvNameDetail.text = user.name
        binding.tvLocation.text = user.location
    }

    fun getImage(imageName: String?): Int  = this.resources.getIdentifier(imageName, "drawable", this.packageName)
}