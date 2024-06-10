package com.example.core.model

data class KnownFor(
    val title: String? = null,
    val popularity: Double,
    val posterPath: String?,
)

data class Crew(
    val adult: Boolean,
    val creditId: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val title: String? = null,
    val originalName: String? = null,
    val popularity: Double,
    val profilePath: String?,
    val posterPath: String?,
)

fun Crew.names(): List<String> = name?.split(" ")?.let {
    if (it.size <= 1) it.plus(" ") else it
} ?: emptyList()

data class Cast(
    val adult: Boolean,
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String? = null,
    val name: String? = null,
    val title: String? = null,
    val order: Int,
    val originalName: String? = null,
    val popularity: Double,
    val department: String?,
    val profilePath: String?,
    val posterPath: String?,
)

fun Cast.names(): List<String> = name?.split(" ")?.let {
    if (it.size <= 1) it.plus("") else it
} ?: emptyList()

data class MovieCredit(
    val id: Int,
    val cast: List<Cast> = listOf(),
    val crew: List<Crew> = listOf(),
)
