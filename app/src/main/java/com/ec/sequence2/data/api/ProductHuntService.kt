package com.ec.sequence2.data.api

import com.ec.sequence2.data.model.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductHuntService {
    @GET("v1/posts?access_token=46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb")
    suspend fun getPosts(): PostsResponse
}