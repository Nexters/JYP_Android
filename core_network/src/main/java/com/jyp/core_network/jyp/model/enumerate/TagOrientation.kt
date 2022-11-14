package com.jyp.core_network.jyp.model.enumerate

import com.google.gson.annotations.SerializedName

enum class TagOrientation {
    @SerializedName("like")
    LIKE,
    @SerializedName("dislike")
    DISLIKE,
    @SerializedName("nomatter")
    NO_MATTER,
}
