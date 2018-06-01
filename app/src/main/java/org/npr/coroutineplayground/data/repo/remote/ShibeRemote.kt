package org.npr.coroutineplayground.data.repo.remote

import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.npr.coroutineplayground.data.model.Shibe
import java.io.IOException

const val ShibeUrl = "http://shibe.online/api/shibes?count=1&urls=true&httpsUrls=true"

suspend fun awaitShibe(): Result<Shibe> {
    val req = Request.Builder().url(ShibeUrl).build()
    val client = Client.instance

    val response = client.newCall(req).await()

    return response.toModel()
}

fun getShibe(): Shibe? {
    val req = Request.Builder().url(ShibeUrl).build()
    val client = Client.instance

    val response = client.newCall(req).execute()

    if (response.isSuccessful) {
        response.body()?.string().let {
            val shibeJsonArray = JSONArray(it)

            for (i in 0 until shibeJsonArray.length()) {
                return Shibe(shibeJsonArray.getString(i))
            }
        }
    }

    return null
}

fun getShibeResult(): Result<Shibe> {
    val req = Request.Builder().url(ShibeUrl).build()
    val client = Client.instance

    val response = client.newCall(req).execute()

    if (response.isSuccessful) {
        response.body()?.string().let {
            try {
                val shibeJsonArray = JSONArray(it)

                for (i in 0 until shibeJsonArray.length()) {
                    return Success(Shibe(shibeJsonArray.getString(i)))
                }
            } catch (e: JSONException) {
                return Failure(e)
            }
        }
    }

    return Failure(IOException("Failed to Shibe"))
}


//suspend fun getShibe(): Shibe? = suspendCoroutine{
//    val req = Request.Builder().url(ShibeUrl).build()
//    val client = OkHttpClient.Builder()
//            .connectTimeout(10, TimeUnit.SECONDS)
//            .build()
//
//    client.newCall(req).enqueue(object: Callback {
//        override fun onFailure(call: Call?, e: IOException?) {
//            it.resume(null)
//        }
//
//        override fun onResponse(call: Call?, response: Response?) {
//            response?.body()?.string().run {
//                val shibeJsonArray = JSONArray(this)
//
//                for (i in 0 until shibeJsonArray.length()) {
//                    it.resume(Shibe(shibeJsonArray.getString(i)))
//                }
//            }
//
//            Log.w("Fallout", "Ended up empty handed")
//        }
//    })
//
//}
