package com.vegan.data

import com.google.firebase.firestore.CollectionReference
import com.vegan.di.module.Recipes
import com.vegan.di.module.Restaurants
import com.vegan.models.Product
import com.vegan.models.Recipe
import com.vegan.models.Restaurant
import durdinapps.rxfirebase2.RxFirestore
import javax.inject.Inject


class RestaurantsRepository @Inject constructor(
    @Restaurants private val restaurants: CollectionReference,
) {

    fun restaurants() = RxFirestore.getCollection(restaurants).map {
        it.toObjects(Restaurant::class.java).sortedBy(Restaurant::createdDate)
    }

}