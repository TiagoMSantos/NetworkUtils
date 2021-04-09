package com.tiagomdosantos.networkutils.lib.utils

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

object ApiErrorUtils {
    fun parseError(response: Response<*>): String {
        val gson = GsonBuilder().create()
        val error: String
        try {
            error = gson.fromJson(response.errorBody()?.string(), String::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d(TAG, it) }
            return ""
        }
        return error
    }
}