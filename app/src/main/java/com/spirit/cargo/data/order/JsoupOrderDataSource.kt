package com.spirit.cargo.data.order

import com.spirit.cargo.domain.order.OrderDataSource
import io.reactivex.rxjava3.core.Single
import org.jsoup.Jsoup
import timber.log.Timber
import java.net.UnknownHostException

class JsoupOrderDataSource : OrderDataSource {
    override fun read(url: String): Single<Int> = Single.create {
        try {
            val doc = loadDoc(url)
            val ordersCount = doc.select("span[id=count_of_request_by_search]")
                .text()
                .toInt()

            it.onSuccess(ordersCount)
        } catch (error: UnknownHostException) {
            it.onSuccess(0)
            Timber.e(error)
        }
    }

    private fun loadDoc(url: String) = Jsoup.connect(url)
        .userAgent("Mozilla")
        .header("Accept", "text/html")
        .header("Accept-Encoding", "gzip,deflate")
        .header(
            "Accept-Language",
            "it-IT,en;q=0.8,en-US;q=0.6,de;q=0.4,it;q=0.2,es;q=0.2"
        )
        .header("Connection", "keep-alive")
        .ignoreContentType(true)
        .get()
}
