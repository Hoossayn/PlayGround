package com.example.core.network.model.response

import com.example.core.model.Video
import com.google.gson.annotations.SerializedName

data class NetworkVideo(
    val id: String,
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("iso_639_1")
    val iso6391: String,
    val key: String,
    val name: String,
    val official: Boolean,
    @SerializedName("published_at")
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String,
)

data class NetworkVideos(val id: Int, val results: List<NetworkVideo>)

fun NetworkVideo.asDomainObject(): Video = Video(
    id = id,
    iso31661 = iso31661,
    iso6391 = iso6391,
    key = key,
    name = name,
    official = official,
    publishedAt = publishedAt,
    site = site,
    size = size,
    type = type,
)
