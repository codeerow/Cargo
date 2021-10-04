package com.spirit.cargo.data.order

import com.spirit.cargo.domain.order.ReadOrders
import io.reactivex.rxjava3.core.Single
import org.jsoup.Jsoup

class JsoupReadOrders : ReadOrders {

    override fun invoke(params: ReadOrders.Params): Single<Int> {
        return Single.create {
            try {
                val doc = loadDoc(params.url)
                val ordersCount = doc.select("span[id=count_of_request_by_search]")
                    .text()
                    .toInt()

                it.onSuccess(ordersCount)
            } catch (error: Throwable) {
                it.onSuccess(0)
            }
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