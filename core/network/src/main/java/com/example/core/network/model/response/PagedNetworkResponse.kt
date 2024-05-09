package com.example.core.network.model.response

import com.google.gson.annotations.SerializedName

data class PagedNetworkResponse<T>(
    val page: Int,
    val results: ArrayList<T> = arrayListOf(),
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
)
