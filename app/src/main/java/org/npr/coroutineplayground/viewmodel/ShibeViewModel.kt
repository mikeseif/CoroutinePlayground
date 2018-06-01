package org.npr.coroutineplayground.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import org.npr.coroutineplayground.data.repo.ShibeRepo

class ShibeViewModel(application: Application): AndroidViewModel(application) {

    private val repo = ShibeRepo(application)

    val shibe = repo.shibe

    fun refreshShibe() {
        repo.refreshShibe()
    }
}