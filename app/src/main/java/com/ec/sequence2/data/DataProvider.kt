package com.ec.sequence2.data

import com.ec.sequence2.data.model.Post
import com.ec.sequence2.data.model.PostsResponse
import com.google.gson.Gson
import java.io.BufferedReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

object DataProvider {

    private val BACKGOURND = Executors.newFixedThreadPool(2)

    private val POST_API_URL =
        "https://api.producthunt.com/v1/posts?access_token=46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb"

    private val gson = Gson()
    fun getPostFromApi(onSuccess: (List<Post>) -> Unit, onError: (Throwable) -> Unit) {
        BACKGOURND.submit {
            try {
                val postRaw = makeCall().orEmpty()
                val postsResponse = gson.fromJson(postRaw, PostsResponse::class.java)
                onSuccess(postsResponse.posts)
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    private fun makeCall(): String? {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        try {
            urlConnection = URL(POST_API_URL).openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connect()

            reader = urlConnection.inputStream?.bufferedReader()
            return reader?.readText()

        } finally {
            urlConnection?.disconnect()
            reader?.close()
        }
    }
}