package com.spirit.cargo.data.request

import com.spirit.cargo.core.RoomTest
import com.spirit.cargo.data.request.model.RequestsDao
import com.spirit.cargo.domain.request.CargoRequest
import org.junit.Before
import org.junit.Test

@Suppress("IllegalIdentifier")
class RoomRequestRepositoryTest : RoomTest() {

    private lateinit var requestsDao: RequestsDao

    @Before
    fun setUpDao() {
        requestsDao = db.requestsDao()
    }

    @Test
    fun `When item added to database then database return list of one item`() {
        // GIVEN
        val sut = RoomRequestRepository(dao = requestsDao)
        val expectedRequest = CargoRequest(1, "1", "2", false, 0)

        // WHEN
        sut.create(expectedRequest.title, expectedRequest.url).blockingAwait()

        // THEN
        sut.observe().test().assertValue(listOf(expectedRequest))
    }

    @Test
    fun `When item deleted from database then database return empty list`() {
        // GIVEN
        val sut = RoomRequestRepository(dao = requestsDao)
        sut.create("", "").blockingAwait()
        val requestToDelete = sut.observe().firstOrError().map { it.first() }.blockingGet()

        // WHEN
        sut.delete(requestToDelete.id).blockingAwait()

        // THEN
        sut.observe().test().assertValue(listOf())
    }

    @Test
    fun `When update item in database then database return updated requests list`() {
        // GIVEN
        val sut = RoomRequestRepository(dao = requestsDao)
        sut.create("", "").blockingAwait()
        val requestToUpdate = sut.observe().firstOrError().map { it.first() }.blockingGet()
        val expectedRequest = CargoRequest(requestToUpdate.id, "1", "2", false, 0)

        // WHEN
        sut.update(expectedRequest).blockingAwait()

        // THEN
        sut.read(expectedRequest.id).test().assertValue(expectedRequest)
    }
}