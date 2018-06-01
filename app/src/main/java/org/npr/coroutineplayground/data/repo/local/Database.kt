package org.npr.coroutineplayground.data.repo.local

import android.arch.persistence.room.Database
import org.npr.coroutineplayground.data.model.Shibe

@Database(
        version  = 1,
        entities = [(Shibe::class)])
abstract class Database: android.arch.persistence.room.RoomDatabase() {

    abstract val shibeDao: ShibeDao

    companion object {
        private const val DB_NAME = "coroutines.db"

        fun createInMemoryDatabase(context: android.content.Context): org.npr.coroutineplayground.data.repo.local.Database
                = android.arch.persistence.room.Room.inMemoryDatabaseBuilder(context.applicationContext, org.npr.coroutineplayground.data.repo.local.Database::class.java)
                .build()

        fun createPersistentDatabase(context: android.content.Context): org.npr.coroutineplayground.data.repo.local.Database
                = android.arch.persistence.room.Room.databaseBuilder(context.applicationContext, org.npr.coroutineplayground.data.repo.local.Database::class.java, org.npr.coroutineplayground.data.repo.local.Database.Companion.DB_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}