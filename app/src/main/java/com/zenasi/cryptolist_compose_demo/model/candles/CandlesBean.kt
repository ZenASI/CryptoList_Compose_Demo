package com.zenasi.cryptolist_compose_demo.model.candles

data class CandlesBean(
    val close: String,
    val high: String,
    val low: String,
    val `open`: String,
    val period: Long,
    val volume: String
)