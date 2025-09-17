package com.lignting.utils

import com.google.gson.Gson

val gson = Gson()

inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}

fun Any.toJson(): String {
    return gson.toJson(this)
}