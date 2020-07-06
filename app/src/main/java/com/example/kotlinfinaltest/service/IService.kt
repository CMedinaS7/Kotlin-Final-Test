package com.example.kotlinfinaltest.service

import com.example.kotlinfinaltest.model.PostModel
import com.example.kotlinfinaltest.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface IService {

    @GET("profile")
    suspend fun getProfile(): Response<UserModel>

    @GET("posts")
    suspend fun getPosts(): Response<List<PostModel>>

    @GET("users")
    suspend fun getUsers(): Response<List<UserModel>>
}