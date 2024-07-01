package com.example.core.test.util

import com.example.core.model.MovieCredit
import com.example.core.model.MovieDetails
import com.example.core.model.Video
import com.example.core.network.model.response.NetworkCast
import com.example.core.network.model.response.NetworkCrew
import com.example.core.network.model.response.NetworkGenre
import com.example.core.network.model.response.NetworkMovieCredit
import com.example.core.network.model.response.NetworkMovieDetails
import com.example.core.network.model.response.NetworkSpokenLanguage
import com.example.core.network.model.response.NetworkVideo
import com.example.core.network.model.response.NetworkVideos
import com.example.core.network.model.response.asDomainObject

object MovieDetailsTestData {
    fun testNetworkMovieDetails(movieId: Long): NetworkMovieDetails = NetworkMovieDetails(
        adult = false,
        backdropPath = "/hZkgoQYus5vegHoetLkCJzb17zJ.jpg",
        budget = 63000000,
        genres =
            listOf(
                NetworkGenre(id = 18, name = "Drama"),
                NetworkGenre(id = 53, name = "Thriller"),
                NetworkGenre(id = 35, name = "Comedy"),
            ),
        homepage = "http://www.foxmovies.com/movies/fight-club",
        id = movieId,
        imdbId = "tt0137523",
        originalLanguage = "en",
        originalTitle = "Fight Club",
        overview =
            """
            A ticking-time-bomb insomniac and a slippery soap salesman channel primal male
             aggression into a shocking new form of therapy. Their concept catches on, with 
             underground \"fight clubs\" forming in every town, until an eccentric gets in the 
             way and ignites an out-of-control spiral toward oblivion.
            """.trimIndent(),
        popularity = 61.416,
        posterPath = "/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        releaseDate = "1999-10-15",
        revenue = 100853753,
        runtime = 139,
        spokenLanguages =
            listOf(
                NetworkSpokenLanguage(
                    englishName = "English",
                    iso6391 = "en",
                    name = "English",
                ),
            ),
        status = "Released",
        tagline = "Mischief. Mayhem. Soap.",
        title = "Fight Club",
        video = false,
        voteAverage = 8.433,
        voteCount = 26280,
    )

    fun testMovieDetails(movieId: Long): MovieDetails = testNetworkMovieDetails(
        movieId = movieId,
    ).asDomainObject()

    fun testNetworkMovieCredit(movieId: Long): NetworkMovieCredit = NetworkMovieCredit(
        id = movieId.toInt(),
        cast = listOf(
            NetworkCast(
                adult = false,
                castId = 4,
                character = "The Narrator",
                creditId = "52fe4250c3a36847f80149f3",
                gender = 2,
                id = 819,
                knownForDepartment = "Acting",
                name = "Edward Norton",
                order = 0,
                originalName = "Edward Norton",
                popularity = 26.99,
                profilePath = "/huV2cdcolEUwJy37QvH914vup7d.jpg",
            ),
            NetworkCast(
                adult = false,
                castId = 0,
                character = "Tyler Durden",
                creditId = "52fe4250c3a36847f80149f7",
                gender = 1,
                id = 1283,
                knownForDepartment = "Acting",
                name = "Helena Bonham Carter",
                order = 2,
                originalName = "Helena Bonham Carter",
                popularity = 22.112,
                profilePath = "/DDeITcCpnBd0CkAIRPhggy9bt5.jpg",
            ),
        ),
        crew = listOf(
            NetworkCrew(
                adult = false,
                creditId = "55731b8192514111610027d7",
                department = "Production",
                gender = 2,
                id = 376,
                job = "Executive Producer",
                knownForDepartment = "Production",
                name = "Arnon Milchan",
                originalName = "Arnon Milchan",
                popularity = 2.931,
                profilePath = "/b2hBExX4NnczNAnLuTBF4kmNhZm.jpg",
            ),
            NetworkCrew(
                adult = false,
                creditId = "5894c4eac3a3685ec6000218",
                department = "Costume & Make-Up",
                gender = 2,
                id = 605,
                job = "Costume Design",
                knownForDepartment = "Costume & Make-Up",
                name = "Michael Kaplan",
                originalName = "Michael Kaplan",
                popularity = 4.294,
                profilePath = "/bNarnI5K4XYIKaHsX6HAitllyQr.jpg",
            ),
        ),
    )

    fun testMovieCredit(movieId: Long): MovieCredit = testNetworkMovieCredit(
        movieId,
    ).asDomainObject()

    fun testNetworkMovieVideos(movieId: Long): NetworkVideos = NetworkVideos(
        id = movieId.toInt(),
        results = listOf(
            NetworkVideo(
                id = "639d5326be6d88007f170f44",
                iso31661 = "en",
                iso6391 = "US",
                key = "O-b2VfmmbyA",
                name = "Fight Club (1999) Trailer - Starring Brad Pitt, Edward Norton, " +
                    "Helena Bonham Carter",
                official = false,
                publishedAt = "2016-03-05T02:03:14.000Z",
                site = "YouTube",
                size = 720,
                type = "Trailer",
            ),
            NetworkVideo(
                id = "5c9294240e0a267cd516835f",
                iso31661 = "en",
                iso6391 = "US",
                key = "282875052",
                name = "#TBT Trailer",
                official = false,
                publishedAt = "2014-10-02T19:20:22.000Z",
                site = "Vimeo",
                size = 1080,
                type = "Trailer",
            ),
        ),
    )

    fun testMovieVideos(movieId: Long): List<Video> = testNetworkMovieVideos(movieId)
        .results.map { it.asDomainObject() }

    fun testUnknownProviderVideo() = listOf(
        NetworkVideo(
            id = "5c9294240e0a267cd516835f",
            iso31661 = "en",
            iso6391 = "US",
            key = "282875052",
            name = "#TBT Trailer",
            official = false,
            publishedAt = "2014-10-02T19:20:22.000Z",
            site = "Unknown",
            size = 1080,
            type = "Trailer",
        ),
    ).map { it.asDomainObject() }
}
