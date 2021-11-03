package com.dhandyjoe.gidhabapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.databinding.ItemUserBinding

class UserAdapter(val data: ArrayList<User>, val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private class MyViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    // code clickListener
    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemUserBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]

        if (holder is MyViewHolder) {
            Glide.with(holder.itemView)
                .load(model.avatar_url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .circleCrop()
                .into(holder.binding.ivProfileUser)
            holder.binding.tvUsernameUser.text = model.login
            holder.itemView.setOnClickListener {
                onItemClickCallback.onItemClicked(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    // add interface
    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    // function to generate image
    fun getImage(imageName: String?): Int  = context.resources.getIdentifier(imageName, "drawable", context.packageName)
}