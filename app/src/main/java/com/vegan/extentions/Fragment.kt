package com.vegan.extentions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.toast(message: String) = requireContext().toast(message)

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()