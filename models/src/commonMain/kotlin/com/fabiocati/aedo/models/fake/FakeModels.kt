package com.fabiocati.aedo.models.fake

import com.fabiocati.aedo.models.CastMember
import com.fabiocati.aedo.models.Movie
import com.fabiocati.aedo.models.MovieDetails
import com.fabiocati.aedo.models.Trailer
import kotlinx.datetime.LocalDate
import kotlin.time.DurationUnit
import kotlin.time.toDuration

val lordOfTheRings = Movie(
    id = 1,
    title = "The Lord of the Rings",
    overview = "Le forze di Sauron hanno attaccato la capitale di Gondor, Minas Tirith, nel suo assedio finale contro l'umanità. Tenuto da un debole sovrintendente, quello che una volta era un fastoso regno non ha mai avuto così bisogno del proprio re. Mentre Gandalf tenta disperatamente di spingere lo smembrato esercito di Gondor ad agire, Thèoden si unisce ai guerrieri di Rohan per combattere assieme a loro. ",
    posterPath = "https://image.tmdb.org/t/p/original/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg",
    backdropPath = "https://image.tmdb.org/t/p/original/2u7zbn8EudG6kLlBzUYqP8RyFU4.jpg",
    logoPath = "https://image.tmdb.org/t/p/original/pkbf6VOHXNDhnXZI4uZQGwfHTR4.png",
)

val lordOfTheRingsDetails = MovieDetails(
    id = 1,
    title = "The Lord of the Rings",
    overview = "Le forze di Sauron hanno attaccato la capitale di Gondor, Minas Tirith, nel suo assedio finale contro l'umanità. Tenuto da un debole sovrintendente, quello che una volta era un fastoso regno non ha mai avuto così bisogno del proprio re. Mentre Gandalf tenta disperatamente di spingere lo smembrato esercito di Gondor ad agire, Thèoden si unisce ai guerrieri di Rohan per combattere assieme a loro. ",
    posterPath = "https://image.tmdb.org/t/p/original/rCzpDGLbOoPwLjy3OAm5NUPOTrC.jpg",
    backdropPath = "https://image.tmdb.org/t/p/original/2u7zbn8EudG6kLlBzUYqP8RyFU4.jpg",
    logoPath = "https://image.tmdb.org/t/p/original/pkbf6VOHXNDhnXZI4uZQGwfHTR4.png",
    genres = listOf("Fantasy"),
    cast = listOf(
        CastMember(name = "Elijah Wood", character = "Frodo Baggins", profilePath = null),
        CastMember(name = "Ian McKellen", character = "Gandalf", profilePath = null),
        CastMember(name = "Viggo Mortensen", character = "Aragorn", profilePath = null),
    ),
    crew = listOf("Fantasy"),
    duration = 120.toDuration(DurationUnit.MINUTES),
    yearOfProduction = LocalDate(2003, 12, 19),
    trailers = listOf(Trailer(
        url = "",
        title = "Trailer #1",
        thumbnail = ""
    )),
    languages = listOf("italian", "english"),
    reviews = emptyList()
)