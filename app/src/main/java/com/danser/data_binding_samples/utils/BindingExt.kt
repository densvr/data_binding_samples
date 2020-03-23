package com.danser.data_binding_samples.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

@BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
fun ImageView.setImageUrl(url: String?, placeHolder: Drawable?) {
    if (url == null) {
        setImageDrawable(placeHolder)
    } else {
        Picasso.get().load(url)
            .apply { if (placeHolder != null) error(placeHolder) }
            .into(this, object: Callback.EmptyCallback() {
                override fun onError(e: Exception?) {
                    super.onError(e)
                }
            })
    }
}

val View.layoutInflater get() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
