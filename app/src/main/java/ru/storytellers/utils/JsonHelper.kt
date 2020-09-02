package ru.storytellers.utils

import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

fun List<Pair<String, String>>.toProperties(): JSONObject {
    val obj = JSONObject()
    try {
        this.map {
            obj.put(it.first, it.second)
        }
    } catch (e: JSONException) {
        Timber.e(e)
    }

    return obj
}
