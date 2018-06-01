package org.npr.coroutineplayground.data.repo.remote

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object Client {
    val instance = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
}