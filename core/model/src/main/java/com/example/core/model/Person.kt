package com.example.core.model

data class Person(
    val id: Long,
    val name: String,
    val popularity: Double,
    val profilePath: String?,
    val knownFor: List<PersonMovie> = emptyList(),
)

fun Person.equalsPerson(other: Person): Boolean = this.id == other.id && this.name == other.name &&
    this.popularity == other.popularity &&
    this.profilePath == other.profilePath && knownFor == other.knownFor

data class PersonMovie(val id: Long, val title: String)
