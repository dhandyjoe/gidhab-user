package com.dhandyjoe.gidhabapp.model


data class User (
    val id: Int,
    val login: String,
    val avatar_url: String,
    val followers_url: String,
    val following_url: String
)
