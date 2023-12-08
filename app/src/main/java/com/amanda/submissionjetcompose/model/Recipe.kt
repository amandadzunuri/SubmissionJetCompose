package com.amanda.submissionjetcompose.model

data class Recipe (
    val id: Int,
    val name: String,
    val ingredients: String,
    val steps: String,
    val cookingTime: String,
    val servings: String,
    val photo: Int,
    var isFavorite: Boolean = false,
)