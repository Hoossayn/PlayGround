package com.example.core.network.model.response

import com.example.core.model.Person
import com.example.core.model.PersonMovie
import com.google.gson.annotations.SerializedName


data class NetworkPerson(
    val id: Long,
    val name: String,
    val popularity: Double,
    @SerializedName("profile_path")
    val profilePath: String?,
    val knownFor: List<NetworkPersonMovie>,
)

data class NetworkPersonMovie(
    val id: Long,
    val title: String?,
)

fun NetworkPersonMovie.toDomainModel(): PersonMovie = PersonMovie(
    id = this.id,
    title = this.title!!,
)

fun NetworkPerson.toDomainModel(): Person = Person(
    id = this.id,
    name = this.name,
    popularity = this.popularity,
    profilePath = this.profilePath,
    knownFor = this.knownFor.filter { it.title != null }.map { it.toDomainModel() },
)
