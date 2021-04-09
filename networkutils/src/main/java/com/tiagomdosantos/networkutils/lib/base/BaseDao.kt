package com.tiagomdosantos.networkutils.lib.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(vararg item: T)

    @Delete
    suspend fun delete(vararg item: T)
}
