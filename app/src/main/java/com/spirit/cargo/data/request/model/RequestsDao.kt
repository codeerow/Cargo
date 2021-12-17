package com.spirit.cargo.data.request.model

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface RequestsDao {
    @Query("SELECT * FROM roomrequestmodel")
    fun readAll(): Observable<List<RoomRequestModel>>

    @Query("SELECT * FROM roomrequestmodel WHERE id = :id")
    fun read(id: Int): Single<RoomRequestModel>

    @Delete
    fun delete(model: RoomRequestModel): Completable

    @Insert
    fun create(model: RoomRequestModel): Completable

    @Update
    fun update(model: RoomRequestModel): Completable
}
