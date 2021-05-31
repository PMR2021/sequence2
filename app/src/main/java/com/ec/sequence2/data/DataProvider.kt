package com.ec.sequence2.data

import com.ec.sequence2.data.api.ProductHuntService
import com.ec.sequence2.data.model.Post
import com.ec.sequence2.data.model.PostsResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

object DataProvider {


    private val BASE_URL = "https://api.producthunt.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(ProductHuntService::class.java)


    suspend fun getPostFromApi(): List<Post> {
        return service.getPosts().posts
    }

}