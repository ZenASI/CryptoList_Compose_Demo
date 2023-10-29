package com.zenasi.cryptolist_compose_demo.activity.info

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenasi.cryptolist_compose_demo.activity.BaseViewModel
import com.zenasi.cryptolist_compose_demo.model.AssetEntity
import com.zenasi.cryptolist_compose_demo.model.asset.AssetBean
import com.zenasi.cryptolist_compose_demo.model.asset.HistoryBean
import com.zenasi.cryptolist_compose_demo.model.asset.MarketsBean
import com.zenasi.cryptolist_compose_demo.repository.CryptoRepo
import com.zenasi.cryptolist_compose_demo.utils.ApiResult
import com.zenasi.cryptolist_compose_demo.utils.DataStoreUtils
import com.zenasi.cryptolist_compose_demo.utils.JsonUtils
import com.zenasi.cryptolist_compose_demo.utils.room.RoomDAO

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class CryptoInfoViewModel @Inject constructor(
    private val repo: CryptoRepo,
    private val jsonUtils: JsonUtils,
    private val dao: RoomDAO,
    private val dataStoreUtils: DataStoreUtils
) : BaseViewModel(dataStoreUtils) {

    var cryptoHistoryList = mutableStateListOf<HistoryBean>()
    var cryptoMarketList: MutableLiveData<List<MarketsBean>> = MutableLiveData(emptyList())
    var assBeanMap: MutableLiveData<Map<String, String>> = MutableLiveData(emptyMap())
    fun assBean2Map(assetBean: AssetBean) =
        run { assBeanMap.value = jsonUtils.moshi2Map(jsonUtils.moshi2Json(assetBean)) }

    // interval	required	m1, m5, m15, m30, h1, h2, h6, h12, d1
    fun getChartInfo(id: String, interval: String?) {
        viewModelScope.launch {
            val map = mutableMapOf<String, String>()
            val range = when (interval) {
                "1D" -> "m5"
                "1W" -> "m30"
                "1M" -> "h2"
                "3M" -> "h6"
                "6M" -> "h12"
                "1Y" -> "d1"
                "ALL" -> "d1"
                else -> "m1"
            }
            val startTime = when (interval) {
                "1D" -> Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli()
                "1W" -> Instant.now().minus(7, ChronoUnit.DAYS).toEpochMilli()
                "1M" -> Instant.now().minus(30, ChronoUnit.DAYS).toEpochMilli()
                "3M" -> Instant.now().minus(90, ChronoUnit.DAYS).toEpochMilli()
                "6M" -> Instant.now().minus(180, ChronoUnit.DAYS).toEpochMilli()
                "1Y" -> Instant.now().minus(365, ChronoUnit.DAYS).toEpochMilli()
                "ALL" -> Instant.ofEpochMilli(1356998400000).toEpochMilli()
                else -> Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli()
            }
            val endTime = Instant.now().toEpochMilli()
            map["interval"] = range
            map["start"] = startTime.toString()
            map["end"] = endTime.toString()
            when (val result = repo.getCryptoHistoryById(id, map)) {
                is ApiResult.OnSuccess -> {
                    val r: List<HistoryBean> =
                        jsonUtils.moshiFromJson(jsonUtils.moshi2Json(result.data.data))
                    cryptoHistoryList.clear()
                    cryptoHistoryList.addAll(r)
                }

                is ApiResult.OnFail -> {

                }

                is ApiResult.OnException -> {

                }
            }
        }
    }

    fun getMarketInfo(id: String) {
        viewModelScope.launch {
            val map = mutableMapOf<String, String>()
            when (val result = repo.getCryptoMarketsById(id, map)) {
                is ApiResult.OnSuccess -> {
                    cryptoMarketList.value =
                        jsonUtils.moshiFromJson<MarketsBean>(jsonUtils.moshi2Json(result.data.data))
                            .filter {
                                it.volumeUsd24Hr.isNotEmpty()
                            }
                }

                is ApiResult.OnFail -> {

                }

                is ApiResult.OnException -> {

                }
            }
        }
    }

    fun insertWatchList(assetBean: AssetBean) {
        viewModelScope.launch(Dispatchers.IO) {
            val state = dao.insertItem(
                AssetEntity(
                    id = assetBean.id,
                    name = assetBean.name,
                    rank = assetBean.rank,
                    symbol = assetBean.symbol
                )
            )
        }
    }
}