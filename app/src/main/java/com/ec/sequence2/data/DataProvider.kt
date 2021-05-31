package com.ec.sequence2.data

import com.ec.sequence2.data.model.Post
import com.ec.sequence2.data.model.PostsResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL

object DataProvider {


    private val POST_API_URL =
        "https://api.producthunt.com/v1/posts?access_token=46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb"

    private val gson = Gson()


    suspend fun getPostFromApi(): List<Post> {
        val postRaw1 = makeCall().orEmpty()
        val postRaw2 = makeCall().orEmpty()
        val postsResponse1 = serialize(postRaw1)
        val postsResponse2 = serialize(postRaw2)

       return postsResponse1.posts + postsResponse2.posts
    }

    private suspend fun serialize(postRaw : String) = withContext(Dispatchers.Default) {
        gson.fromJson(postRaw, PostsResponse::class.java)
    }

    suspend fun getPostFromApiAsync(): List<Post> = supervisorScope {
        val postRaw1Deferred = async {
            makeCall().orEmpty()
        }
        val postRaw2Deferred = async {
            makeCall().orEmpty()
        }
        val postsResponse1Deferred = async {
            serialize(postRaw1Deferred.await())
        }

        val postsResponse2Deferred = async {
            serialize(postRaw2Deferred.await())
        }

        postsResponse1Deferred.await().posts + postsResponse2Deferred.await().posts
    }


    private suspend fun makeCall(): String? =  withContext(Dispatchers.IO) {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        try {
            urlConnection = URL(POST_API_URL).openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()

            reader = urlConnection.inputStream?.bufferedReader()

            reader?.readText()

        } finally {
            urlConnection?.disconnect()
            reader?.close()
        }
    }
}