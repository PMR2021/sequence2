package com.ec.sequence2.data.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val id : String,
    @SerializedName("name")
    val title: String,
    @SerializedName("tagline")
    val subTitle: String
)