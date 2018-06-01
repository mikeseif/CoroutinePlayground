package org.npr.coroutineplayground.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Shibe(@PrimaryKey val imgUrl: String)