package com.vegan.data

import com.google.firebase.firestore.CollectionReference
import com.vegan.di.module.Recipes
import com.vegan.models.Product
import com.vegan.models.Recipe
import durdinapps.rxfirebase2.RxFirestore
import javax.inject.Inject


class RecipeRepository @Inject constructor(
    @Recipes private val recipes: CollectionReference,
) {

    fun recipes() = RxFirestore.getCollection(recipes).map {
        it.toObjects(Recipe::class.java).sortedBy(Recipe::createdDate)
    }

}