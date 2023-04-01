package com.kg.taskapp.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage( url: String?){
    Glide.with(this).load(url).into(this)
}
fun showAutoKeyboard(context: Context, editText: EditText) {
    editText.requestFocus()
    editText.postDelayed({
        val keyboard = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.showSoftInput(editText, 0)
                         }, 200)
}