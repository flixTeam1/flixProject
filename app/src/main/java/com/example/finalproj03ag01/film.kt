package com.example.finalproj03ag01

import com.google.gson.annotations.SerializedName

class film {
    @SerializedName("title")
    // movie title from API
    var film_t: String? = null

    // Overview form API
    @SerializedName("overview")
    // movie description
    var film_des: String? = null

    // poster image form API
    @SerializedName("poster_path")
    // movie poster
    var film_poster: String? = null
}