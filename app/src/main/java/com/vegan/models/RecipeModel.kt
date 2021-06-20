package com.vegan.models

sealed class RecipeModel {

    data class Description(
        val text: String
    ) : RecipeModel()

    data class Title(
        val text: String
    ) : RecipeModel()

    data class Ingredient(
        val name: String,
        val count: String?
    ) : RecipeModel()

}