package com.example.core.test.util

import com.example.core.network.model.response.NetworkTvShow
import com.example.core.network.model.response.PagedNetworkResponse
import com.example.core.network.model.response.asDomainObject

object TvShowsTestData {
    fun testTVShows() = testNetworkTVShows().results.map { it.asDomainObject() }

    fun testNetworkTVShows() = PagedNetworkResponse(
        page = 1,
        results = arrayListOf(
            NetworkTvShow(
                backdropPath = "/jWXrQstj7p3Wl5MfYWY6IHqRpDb.jpg",
                firstAirDate = "1952-12-26",
                genreIds = listOf(10763),
                id = 1112,
                name = "Tagesschau",
                originCountry = listOf("DE"),
                originalLanguage = "de",
                originalName = "Tagesschau",
                overview = """
                    German daily news program, the oldest still existing program 
                    on German television.
                """.trimIndent(),
                popularity = 6.7,
                posterPath = "/7dFZJ2ZJJdcmkp05B9NWlqTJ5tq.jpg",
                voteAverage = 8.9,
                voteCount = 4192,
            ),
            NetworkTvShow(
                backdropPath = "/l7LRGYJY3NzIGBlpvHpMsNXHbm5.jpg",
                firstAirDate = "falli",
                genreIds = listOf(
                    10751,
                    35,
                ),
                id = 1112,
                name = "Mom for rent",
                originCountry = listOf("SK"),
                originalLanguage = "Mama na prenájom",
                originalName = "Ahmad Bauer",
                overview = """
                    Abandoned by his wife, Martin is lying to his daughter not to be upset. 
                    But as Hanka grows, these lies become unbearable. Martin meets Nada unexpectedly, 
                    asked her to be a rent-a-mother and all lives are completely changed.
                """.trimIndent(),
                popularity = 6.7,
                posterPath = "/fH7PP2Rkdlo414IHvZABBHhtoqd.jpg",
                voteAverage = 8.9,
                voteCount = 4192,
            ),
            NetworkTvShow(
                backdropPath = "/218ZehBKlH8efPRRccmB7bu0oLQ.jpg",
                firstAirDate = "falli",
                genreIds = listOf(
                    35,
                    9648,
                    10766,
                    18,
                ),
                id = 1112,
                name = "Elas por Elas",
                originCountry = listOf("BR"),
                originalLanguage = "appareat",
                originalName = "Ahmad Bauer",
                overview = """
                    Seven friends who met in their youth at an English course meet again 25 
                    years later; Lara, Taís, Helena, Adriana, Renée, Natália and Carol, each 
                    of them has a different personality and origin, but they share a deep affection.
                """.trimIndent(),
                popularity = 6.7,
                posterPath = "/raDj1xSVzBenwI87arenZY6eHmz.jpg",
                voteAverage = 8.9,
                voteCount = 4192,
            ),
            NetworkTvShow(
                backdropPath = "tempus",
                firstAirDate = "falli",
                genreIds = listOf(
                    18,
                    35,
                ),
                id = 1112,
                name = "Ulice",
                originCountry = listOf("CZ"),
                originalLanguage = "appareat",
                originalName = "Ahmad Bauer",
                overview = """
                    Ulice is a Czech soap opera produced and broadcast by Nova. In the Czech language
                     Ulice means street.\n\nThe show describes the lives of the Farský, Jordán, Boháč, 
                     Nikl, and Liška families and many other people that live in Prague. Their daily 
                     battle against real problems of living in a modern world like divorce, love, betrayal 
                     and illness or disease. Ulice often shows crime.
                """.trimIndent(),
                popularity = 6.7,
                posterPath = "/aOPhyvHDauWFuc3rthpHArCNyrm.jpg",
                voteAverage = 8.9,
                voteCount = 4192,
            ),
        ),
        totalPages = 15,
        totalResults = 200,
    )
}
