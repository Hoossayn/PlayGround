package com.example.core.network.service

import com.example.core.network.Config
import com.example.core.network.model.response.NetworkPerson
import com.example.core.network.model.response.PagedNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PeopleListApi {
    /**
     * Get a list of people ordered by popularity.
     *
     * @param page
     * @return
     */
    @GET("${Config.TMDB_API_V3}/person/popular")
    suspend fun popularPersons(
        @Query("page") page: Int,
    ): PagedNetworkResponse<NetworkPerson>
}
