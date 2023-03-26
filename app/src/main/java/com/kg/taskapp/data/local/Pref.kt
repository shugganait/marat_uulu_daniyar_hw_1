package com.kg.taskapp.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.net.Uri

class Pref(context: Context) {

    private val pref = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)

    fun isUserSeen(): Boolean {
        return pref.getBoolean(ONBOARD_KEY, false)
    }


    fun saveSeen(){
        pref.edit().putBoolean(ONBOARD_KEY, true).apply()
    }

    fun saveNickName(name:String?) {
        pref.edit().putString(KEY_FOR_NICKNAME, name).apply()
    }

    fun getNickName(): String {
        return pref.getString(KEY_FOR_NICKNAME, "").toString()
    }

    fun saveProfilePicture(uri: String) {
        pref.edit().putString(KEY_FOR_PICTURE, uri).apply()
    }

    fun getProfilePicture(): String {
        return pref.getString(KEY_FOR_PICTURE, "").toString()
    }

    companion object{
        const val PREF_NAME="Салю"
        const val ONBOARD_KEY="ключ"
        const val KEY_FOR_NICKNAME="САЛЮЮЮЮ"
        const val KEY_FOR_PICTURE="CFkddddd"
    }
}
