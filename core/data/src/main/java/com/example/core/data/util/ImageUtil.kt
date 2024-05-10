package com.example.core.data.util

object ImageUtil {
    sealed interface Size {
        val value: String

        enum class BackDrop(override val value: String) : Size {
            W300("w300"),
            W780("w780"),
            W1280("w1280"),
        }

        enum class Logo(override val value: String) : Size {
            W45("w45"),
            W92("w92"),
            W154("w154"),
            W185("w185"),
            W300("w300"),
            W500("w500"),
        }

        enum class Poster(override val value: String) : Size {
            W92("w92"),
            W154("w154"),
            W185("w185"),
            W342("w342"),
            W500("w500"),
            W780("w780"),
        }

        enum class Profile(override val value: String) : Size {
            W45("w45"),
            W185("w185"),
            H632("h632"),
        }

        enum class Still(override val value: String) : Size {
            W92("w92"),
            W185("w185"),
            W300("w300"),
        }

        data object Original : Size {
            override val value: String
                get() = "original"
        }
    }

    fun buildImageUrl(
        path: String?,
        size: Size = Size.Original,
    ): String = "https://image.tmdb.org/t/p/${size.value}/$path"
}
