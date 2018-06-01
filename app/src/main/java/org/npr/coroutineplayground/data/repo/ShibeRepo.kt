package org.npr.coroutineplayground.data.repo

import android.content.Context
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import org.npr.coroutineplayground.Dispatchers
import org.npr.coroutineplayground.data.repo.local.Database
import org.npr.coroutineplayground.data.repo.remote.Failure
import org.npr.coroutineplayground.data.repo.remote.Success
import org.npr.coroutineplayground.data.repo.remote.awaitShibe

class ShibeRepo(ctx: Context) {

    private val db = Database.createPersistentDatabase(ctx.applicationContext)

    val shibe = db.shibeDao.observeShibe()

    private var refreshJob: Job? = null

    fun refreshShibe() {
        refreshJob?.cancel()
        refreshJob = launch {

            val result = withContext(Dispatchers.dispatchers.network) {
                awaitShibe()
            }

            when (result) {
                is Success -> {
                    withContext(Dispatchers.dispatchers.database) {
                        db.shibeDao.delete()
                        db.shibeDao.insert(result.data)
                    }
                }
                is Failure -> result.error?.printStackTrace()
            }
        }
    }
}