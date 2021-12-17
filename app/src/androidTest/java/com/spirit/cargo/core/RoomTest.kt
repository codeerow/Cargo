package com.spirit.cargo.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.spirit.cargo.app.persistence.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.Rule

open class RoomTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    protected lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        db.close()
    }

}