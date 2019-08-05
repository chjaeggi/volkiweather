package com.chjaeggi.volkiweather.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

object DataBindingAdapters {

    /**
     * Set an ImageView's icon/image by providing the image resource's ID.
     */
    @BindingAdapter("android:src")
    @JvmStatic
    fun ImageView.setIconResourceId(id: Int?) {
        id?.let {
            setImageResource(it)
        }
    }
}
