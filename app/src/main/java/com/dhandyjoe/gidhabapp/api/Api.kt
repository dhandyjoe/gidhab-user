package com.dhandyjoe.gidhabapp.api

import com.dhandyjoe.gidhabapp.BuildConfig
import com.dhandyjoe.gidhabapp.model.DetailUser
import com.dhandyjoe.gidhabapp.model.User
import com.dhandyjoe.gidhabapp.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getListFollowers (
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getListFollowing (
        @Path("username") username: String
    ): Call<ArrayList<User>>
}