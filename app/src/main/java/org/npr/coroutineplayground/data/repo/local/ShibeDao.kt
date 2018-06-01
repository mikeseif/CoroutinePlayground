package org.npr.coroutineplayground.data.repo.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import org.npr.coroutineplayground.data.model.Shibe

@Dao
interface ShibeDao {
    @Query("SELECT * FROM shibe")
    fun observeShibe(): LiveData<Shibe>

    @Insert
    fun insert(shibe: Shibe)

    @Query("DELETE FROM shibe")
    fun delete()
}