package com.goda.elmenus.util.extension

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import androidx.core.widget.NestedScrollView


fun NestedScrollView.setupOutline(radius: Int) {
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            view?.let {
                outline?.setRoundRect(0, 0, it.width, (it.height + radius), radius.toFloat())
            }
        }
    }
    clipToOutline = true
}

