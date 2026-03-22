package com.fabiocati.aedo.models

data class Review(
    val author: String,
    val content: String,
    val authorAvatarUrl: String?,
    val rating: Double?,
    val createdAt: String
)
