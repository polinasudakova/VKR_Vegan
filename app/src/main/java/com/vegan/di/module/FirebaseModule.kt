package com.vegan.di.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class FirebaseModule {

    @Provides
    fun provideAuth() = Firebase.auth

    @Provides
    fun provideStorage() = Firebase.storage.reference

    @Provides
    fun provideFirestore() = Firebase.firestore

}