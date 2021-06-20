package com.vegan.models


data class User(
    val id: String = "",
    val firstName: String = "",
    val secondName: String = "",
    val phone: String = "",
    val avatarUrl: String = "",
    val refrigerator: List<String> = emptyList(),
    val stopList: List<String> = emptyList(),
    val favorites: List<String> = emptyList(),
)