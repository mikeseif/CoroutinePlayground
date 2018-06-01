package org.npr.coroutineplayground

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineDispatcher
import kotlinx.coroutines.experimental.android.UI

data class AppCoroutineDispatchers(
        val database: CoroutineDispatcher,
        val disk: CoroutineDispatcher,
        val network: CoroutineDispatcher,
        val main: CoroutineDispatcher
)

object Dispatchers {
    var dispatchers = AppCoroutineDispatchers(
            CommonPool,
            CommonPool,
            CommonPool,
            UI)
}