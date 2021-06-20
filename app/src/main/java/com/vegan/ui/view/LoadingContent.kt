package com.vegan.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.res.getResourceIdOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.vegan.R


class LoadingContent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val progressView: View = LayoutInflater.from(context).inflate(R.layout.view_fullscreen_progressbar, this, false)

    private lateinit var contentView: View
    private var contentId: Int = 0

    private var isContentVisible = false

    init {
        addView(progressView)
        context.withStyledAttributes(attrs, R.styleable.LoadingContent) {
            isContentVisible = getBoolean(R.styleable.LoadingContent_showContent, true)
            contentId = getResourceIdOrThrow(R.styleable.LoadingContent_contentId)
        }
    }

    fun showContent() {
        progressView.isVisible = false
        findViewById<View>(contentId).isVisible = true
    }

    fun showProgress() {
        progressView.isVisible = true
        findViewById<View>(contentId).isVisible = false
    }

}