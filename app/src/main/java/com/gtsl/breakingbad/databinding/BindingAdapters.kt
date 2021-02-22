package com.gtsl.breakingbad.databinding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageFromUrl")
    fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("circularImageFromUrl")
    fun bindCircularImageFromUrl(view: ImageView, imageUrl: String?) {
        imageUrl?.let {
            Glide.with(view.context)
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("blur")
    fun bindBlur(view: ImageView, url: String?) {
        url?.let {
            Glide.with(view.context)
                .load(it)
                .apply(RequestOptions.bitmapTransform(BlurTransformation()))
                .into(view)
        }
    }

    @JvmStatic
    @BindingAdapter("textList")
    fun bindTextList(view: TextView, textList: List<String>?) {
        textList?.let {
            view.text = it.joinToString(separator = "\n")
        }
    }
}