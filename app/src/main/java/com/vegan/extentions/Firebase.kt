package com.vegan.extentions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


fun FirebaseUser?.requiredUid(): String =
    this?.uid ?: throw IllegalStateException("Пользователь не авторизован")

fun FirebaseAuth.requiredCurrentUserUid(): String =
    currentUser?.uid ?: throw IllegalStateException("Пользователь не авторизован")