package com.pankaj.flipkart

import com.google.gson.annotations.SerializedName

data class Pictionary(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("imageUrl")
    var imageUrl: String = "",
    @SerializedName("difficulty")
    var difficulty: Int = 0,
    @SerializedName("answer")
    var answer: String = "",
    var isShown: Boolean = false
)