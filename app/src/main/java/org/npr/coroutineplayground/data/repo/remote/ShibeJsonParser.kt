package org.npr.coroutineplayground.data.repo.remote

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.npr.coroutineplayground.data.model.Shibe

class ShibeJsonParser: JsonParser<Shibe> {
    override fun fromJson(jsonObject: JSONObject): Shibe {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fromJson(jsonArray: JSONArray): Shibe {
        for (i in 0 until jsonArray.length()) {
            return Shibe(jsonArray.getString(i))
        }

        throw JSONException("No items in JSONArray")
    }

}