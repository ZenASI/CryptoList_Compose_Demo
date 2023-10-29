package com.zenasi.cryptolist_compose_demo.model.asset

import android.os.Parcelable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.squareup.moshi.JsonClass
import com.zenasi.cryptolist_compose_demo.PriceState
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class AssetBean(
    val changePercent24Hr: String = "",
    val explorer: String = "",
    val id: String = "",
    val marketCapUsd: String = "",
    val maxSupply: String = "",
    val name: String = "",
    val priceUsd: String = "",
    val rank: String = "",
    val supply: String = "",
    val symbol: String = "",
    val volumeUsd24Hr: String = "",
    val vwap24Hr: String = "",
) : Parcelable {

    @IgnoredOnParcel
    var priceState by mutableStateOf(PriceState.same)

    @IgnoredOnParcel
    var pUsd by mutableStateOf(priceUsd)

    @IgnoredOnParcel
    var p24H by mutableStateOf(changePercent24Hr)

    fun getFakeAsset(): AssetBean {
        return AssetBean(
            id = "tether",
            rank = "3",
            symbol = "USDT",
            name = "Tether",
            supply = "68371859091.2881850000000000",
            maxSupply = "",
            marketCapUsd = "68451169253.6793029146188985",
            volumeUsd24Hr = "9663681796.3518419051233414",
            priceUsd = "1.0011599825344112",
            changePercent24Hr = "0.0257849920205583",
            vwap24Hr = "1.0005751828797815",
            explorer = "https://www.omniexplorer.info/asset/31"
        )
    }
}