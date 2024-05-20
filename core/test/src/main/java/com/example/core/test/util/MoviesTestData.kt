package com.example.core.test.util

import com.example.core.model.Movie


fun testMovie(): Movie = Movie(
    id = 667538,
    title = "Transformers: Rise of the Beasts",
    overview =
        """
        When a new threat capable of destroying the entire planet emerges, Optimus Prime and 
        the Autobots must team up with a powerful faction known as the Maximals. With the 
        fate of humanity hanging in the balance, humans Noah and Elena will do whatever it takes 
        to help the Transformers as they engage in the ultimate battle to save Earth.
        """.trimIndent(),
    backdropPath = "/bz66a19bR6BKsbY8gSZCM4etJiK.jpg",
    posterPath = "/2vFuG6bWGyQUzYS9d69E5l85nIz.jpg",
    voteAverage = 7.5,
)

fun testMovies(): List<Movie> = listOf(
    Movie(
        id = 667538,
        title = "Transformers: Rise of the Beasts",
        overview =
            """
            When a new threat capable of destroying the entire planet emerges, Optimus Prime and 
            the Autobots must team up with a powerful faction known as the Maximals. With the 
            fate of humanity hanging in the balance, humans Noah and Elena will do whatever it takes 
            to help the Transformers as they engage in the ultimate battle to save Earth.
            """.trimIndent(),
        backdropPath = "/bz66a19bR6BKsbY8gSZCM4etJiK.jpg",
        posterPath = "/2vFuG6bWGyQUzYS9d69E5l85nIz.jpg",
        voteAverage = 7.5,
    ),
    Movie(
        id = 298618,
        title = "The Flash",
        overview =
            """
            When his attempt to save his family inadvertently alters the future, 
            Barry Allen becomes trapped in a reality in which General Zod has returned and 
            there are no Super Heroes to turn to. In order to save the world that he is in and 
            return to the future that he knows, Barry's only hope is to race for his life. But 
            will making the ultimate sacrifice be enough to reset the universe
            """.trimIndent(),
        backdropPath = "/yF1eOkaYvwiORauRCPWznV9xVvi.jpg",
        posterPath = "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
        voteAverage = 7.0,
    ),
    Movie(
        id = 884605,
        title = "No Hard Feelings",
        overview =
            """
            On the brink of losing her childhood home, Maddie discovers an intriguing job listing: 
            wealthy helicopter parents looking for someone to “date” their introverted 19-year-old 
            son, Percy, before he leaves for college. To her surprise, Maddie soon discovers the 
            awkward Percy is no sure thing.
            """.trimIndent(),
        backdropPath = "/rRcNmiH55Tz0ugUsDUGmj8Bsa4V.jpg",
        posterPath = "/4K7gQjD19CDEPd7A9KZwr2D9Nco.jpg",
        voteAverage = 7.1,
    ),
    Movie(
        id = 346698,
        title = "Barbie",
        overview =
            """
           Barbie and Ken are having the time of their lives in the colorful and 
           seemingly perfect world of Barbie Land. However, when they get a chance 
           to go to the real world, they soon discover the joys and perils of 
           living among humans.
            """.trimIndent(),
        backdropPath = "/nHf61UzkfFno5X1ofIhugCPus2R.jpg",
        posterPath = "/iuFNMS8U5cb6xfzi51Dbkovj7vM.jpg",
        voteAverage = 7.5,
    ),
)
