package com.fabiocati.aedo.model

data class MovieHomeModel(
    override val bannerUrl: String,
    override val posterUrl: String,
    override val title: String,
    override val shortDescription: String,
    override val logoUrl: String?
) : HomeModel


val mickey17Data = MovieHomeModel(
    title = "Mickey 17 (2025)",
    shortDescription = "Il suo lavoro è morire per salvare l'umanità.",
    posterUrl = "https://image.tmdb.org/t/p/original/2JQBZs01ahsOiANQ7Fs8MN9zBY4.jpg",
    bannerUrl = "https://image.tmdb.org/t/p/original/hGLywNhy1Fo1rNFHsNZsXGS69B8.jpg",
    logoUrl = "https://image.tmdb.org/t/p/original/hwJyHDKGUa3jHdrUfnayXn92Aw1.png"
)