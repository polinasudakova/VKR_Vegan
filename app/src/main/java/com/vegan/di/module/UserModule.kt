package com.vegan.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class UserModule {

    @Users
    @Provides
    fun provideUsers(firestore: FirebaseFirestore) = firestore.collection("users")

    @Refrigerator
    @Provides
    fun provideRefrigerator(firestore: FirebaseFirestore) = firestore.collection("refrigerator")

    @StopList
    @Provides
    fun provideStopList(firestore: FirebaseFirestore) = firestore.collection("stop-list")

    @Recipes
    @Provides
    fun provideRecipes(firestore: FirebaseFirestore) = firestore.collection("recipes")

    @Restaurants
    @Provides
    fun provideRestaurants(firestore: FirebaseFirestore) = firestore.collection("restaurants")

    @UserId
    @Provides
    fun provideCurrentUserId(firebaseAuth: FirebaseAuth): String? = firebaseAuth.currentUser?.uid
}