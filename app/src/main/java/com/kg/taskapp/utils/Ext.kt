package com.kg.taskapp.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

fun ImageView.loadImage( url: String?){
    Glide.with(this).load(url).into(this)
}