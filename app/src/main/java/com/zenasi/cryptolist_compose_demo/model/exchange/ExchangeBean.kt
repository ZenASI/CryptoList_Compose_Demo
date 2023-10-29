package com.zenasi.cryptolist_compose_demo.model.exchange

data class ExchangeBean(
    val exchangeUrl: String,
    val id: String,
    val name: String,
    val percentTotalVolume: String,
    val rank: String,
    val socket: Boolean,
    val tradingPairs: String,
    val updated: Long,
    val volumeUsd: String
)