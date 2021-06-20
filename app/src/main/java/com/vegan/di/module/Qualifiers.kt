package com.vegan.di.module

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserDocument

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserId

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Users

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Refrigerator

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class StopList

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Recipes

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Restaurants