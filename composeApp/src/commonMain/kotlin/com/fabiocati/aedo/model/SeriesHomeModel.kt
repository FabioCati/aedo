package com.fabiocati.aedo.model

data class SeriesHomeModel(
    override val bannerUrl: String,
    override val posterUrl: String,
    override val title: String,
    override val shortDescription: String,
    override val logoUrl: String?
) : HomeModel
