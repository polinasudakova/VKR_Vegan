package com.vegan.ui.view

import android.view.View
import androidx.core.view.isVisible


class ScreenStateController(
    private val progressView: View,
    private val contentView: View
) {

    fun showContent() {
        progressView.isVisible = false
        contentView.isVisible = true
    }

    fun showProgress() {
        progressView.isVisible = true
        contentView.isVisible = false
    }

}