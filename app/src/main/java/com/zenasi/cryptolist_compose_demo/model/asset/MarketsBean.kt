package com.zenasi.cryptolist_compose_demo.model.asset

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarketsBean(
    val baseId: String = "",
    val baseSymbol: String = "",
    val exchangeId: String = "",
    val priceUsd: String = "",
    val quoteId: String = "",
    val quoteSymbol: String = "",
    val volumePercent: String = "",
    val volumeUsd24Hr: String = ""
)