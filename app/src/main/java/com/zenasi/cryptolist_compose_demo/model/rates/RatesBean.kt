package com.zenasi.cryptolist_compose_demo.model.rates

data class RatesBean(
    val currencySymbol: String,
    val id: String,
    val rateUsd: String,
    val symbol: String,
    val type: String
)