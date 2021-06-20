package com.vegan.models

import android.net.Uri

sealed class UserRegistration(open val phone: String) {

    data class SignUp(
        override val phone: String,
        val firstName: String,
        val secondName: String,
        val avatarUri: Uri
    ) : UserRegistration(phone)

    data class Login(
        override val phone: String
    ) : UserRegistration(phone)

}