package com.example.core.network.service


import com.example.core.network.Config
import com.example.core.network.model.response.NetworkTvShow
import com.example.core.network.model.response.PagedNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TVShowsListApi {
    /**
     * Get a list of TV shows airing today.
     *
     * @param language
     * @param page
     * @param region
     * @return
     */
    @GET("${Config.TMDB_API_V3}/tv/airing_today")
    suspend fun airingToday(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkTvShow>

    /**
     * Get a list of TV shows that air in the next 7 days.
     *
     * @param language
     * @param page
     * @param region
     * @return
     */
    @GET("${Config.TMDB_API_V3}/tv/on_the_air")
    suspend fun onTheAir(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkTvShow>

    /**
     * Get a list of TV shows ordered by popularity.
     *
     * @param language
     * @param page
     * @param region
     * @return
     */
    @GET("${Config.TMDB_API_V3}/tv/popular")
    suspend fun popular(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkTvShow>

    /**
     * Get a list of TV shows ordered by rating.
     *
     * @param language
     * @param page
     * @param region
     * @return
     */
    @GET("${Config.TMDB_API_V3}/tv/top_rated")
    suspend fun topRated(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String? = null,
    ): PagedNetworkResponse<NetworkTvShow>
}
