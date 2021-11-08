package com.dhandyjoe.gidhabapp.api

import com.dhandyjoe.gidhabapp.model.DetailUser
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getListFollowers (
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getListFollowing (
        @Path("username") username: String
    ): Call<ArrayList<User>>
}