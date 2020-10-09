package com.example.promtnow_rot

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "shared_prefs_name"
        private const val SET_ID_LIST = "setID"
        private const val SET_ID_FORM = "setIDForm"
        private const val SET_CHECK = "setCheck"
        private const val HAVE_REQUEST = "CheckList"
        private const val IMAGE = "image"
    }
    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var rememberCheck: Boolean
        get() = sharedPrefs.getBoolean(SET_CHECK, false)
        set(value) = sharedPrefs.edit().putBoolean(SET_CHECK, value).apply()
    var haveRequest: Boolean
        get() = sharedPrefs.getBoolean(HAVE_REQUEST, false)
        set(value) = sharedPrefs.edit().putBoolean(HAVE_REQUEST, value).apply()
    var idList:Int
        get() = sharedPrefs.getInt(SET_ID_LIST,0)
        set(value) = sharedPrefs.edit().putInt(SET_ID_LIST,value).apply()
    var idForm:Int
        get() = sharedPrefs.getInt(SET_ID_FORM,0)
        set(value) = sharedPrefs.edit().putInt(SET_ID_FORM,value).apply()
    var image: String?
        get() = sharedPrefs.getString(IMAGE,"")
        set(value) = sharedPrefs.edit().putString(IMAGE,value).apply()
}