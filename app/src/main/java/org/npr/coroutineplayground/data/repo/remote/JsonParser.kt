package org.npr.coroutineplayground.data.repo.remote

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

interface JsonParser<out T> {
    @Throws(JSONException::class)
    fun fromJson(jsonObject: JSONObject): T

    @Throws(JSONException::class)
    fun fromJson(jsonArray: JSONArray): T
}