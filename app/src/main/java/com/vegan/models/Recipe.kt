package com.vegan.models

import java.util.*


data class Recipe(
    val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val calorieContent: String = "",
    val cookingMethod: List<String> = emptyList(),
    val ingredients: Map<String, String> = emptyMap(),
    val createdDate: Date = Date(),
)