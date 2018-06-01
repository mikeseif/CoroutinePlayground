package org.npr.coroutineplayground.data.repo.remote

import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.npr.coroutineplayground.data.model.Shibe
import java.io.IOException

suspend fun Call.await(): Response {
    return suspendCancellableCoroutine {
        continuation ->
            enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    if (continuation.isCancelled) { return }

                    val thrown = e ?: IOException("Unknown failure")
                    continuation.resumeWithException(thrown)
                }

                override fun onResponse(call: Call?, response: Response?) {
                    if (response != null) {
                        continuation.resume(response)
                    } else {
                        continuation.resumeWithException(IOException("No response"))
                    }
                }
            })

        continuation.invokeOnCompletion {
            if (continuation.isCancelled) {
                try {
                    cancel()
                } catch (_: Throwable) { }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T: Any> Response.toModel(): Result<T> {

    if (isSuccessful) {
        body()?.string().let {
            try {
                val parser: JsonParser<T> = when (T::class) {
                    Shibe::class -> ShibeJsonParser() as JsonParser<T>
                    else -> return Failure(IOException("No parser found for type T: ${T::class}"))
                }

                val ja = JSONArray(it)
                return try {
                    Success(parser.fromJson(ja))
                } catch (e: JSONException) {
                    Failure(e)
                }

            } catch (e: JSONException) {
                return Failure(e)
            }
        }
    }

    return Failure(IOException("Failed to Shibe"))
}