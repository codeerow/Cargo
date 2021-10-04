package com.spirit.cargo.app.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.spirit.cargo.data.request.model.RequestsDao
import com.spirit.cargo.data.request.model.RoomRequestModel


@Database(
    entities = [RoomRequestModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun requestsDao(): RequestsDao
}