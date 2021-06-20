package com.vegan.data

import com.google.firebase.firestore.CollectionReference
import com.vegan.di.module.Refrigerator
import com.vegan.di.module.StopList
import com.vegan.models.Product
import durdinapps.rxfirebase2.RxFirestore
import javax.inject.Inject


class ProductsRepository @Inject constructor(
    @Refrigerator private val refrigerator: CollectionReference,
    @StopList private val stopList: CollectionReference,
) {

    fun refrigeratorProducts() = RxFirestore.getCollection(refrigerator)
        .map { it.toObjects(Product::class.java).sortedBy(Product::name) }

    fun stopListProducts() = RxFirestore.getCollection(stopList)
        .map { it.toObjects(Product::class.java).sortedBy(Product::name) }

}