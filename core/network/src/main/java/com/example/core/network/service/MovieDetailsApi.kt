package com.example.core.network.service

import com.example.core.network.Config
import com.example.core.network.model.response.NetworkMovie
import com.example.core.network.model.response.NetworkMovieDetails
import com.example.core.network.model.response.NetworkMovieCredit
import com.example.core.network.model.response.NetworkVideos
import com.example.core.network.model.response.PagedNetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApi {
    /**
     * Get the top level details of a movie by ID.
     *
     * @param movieId is the ID of the movie
     * @return
     */
    @GET("${Config.TMDB_API_V3}/movie/{movieId}")
    suspend fun movieDetails(
        @Path("movieId") movieId: Long,
    ): NetworkMovieDetails

    /**
     * Get the list of casts and crews of a movie by ID.
     *
     * @param movieId is the ID of the movie
     * @return
     */
    @GET("${Config.TMDB_API_V3}/movie/{movieId}/credits")
    suspend fun movieCredits(
        @Path("movieId") movieId: Long,
    ): NetworkMovieCredit

    /**
     * Get videos of a movie by ID.
     *
     * @param movieId is the ID of the movie
     * @return
     */
    @GET("${Config.TMDB_API_V3}/movie/{movieId}/videos")
    suspend fun movieVideos(
        @Path("movieId") movieId: Long,
    ): NetworkVideos

    /**
     * Get the similar movies based on genres and keywords.
     *
     * @param movieId is the ID of the movie
     * @return
     */
    @GET("${Config.TMDB_API_V3}/movie/{movieId}/similar")
    suspend fun similar(
        @Path("movieId") movieId: Long,
    ): PagedNetworkResponse<NetworkMovie>
}
