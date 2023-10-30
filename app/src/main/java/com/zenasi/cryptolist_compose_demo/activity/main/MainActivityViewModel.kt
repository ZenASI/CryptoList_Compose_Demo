package com.zenasi.cryptolist_compose_demo.activity.main

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenasi.cryptolist_compose_demo.CryptoScreen
import com.zenasi.cryptolist_compose_demo.PriceState
import com.zenasi.cryptolist_compose_demo.activity.BaseViewModel
import com.zenasi.cryptolist_compose_demo.model.AssetEntity
import com.zenasi.cryptolist_compose_demo.model.asset.AssetBean
import com.zenasi.cryptolist_compose_demo.model.asset.HistoryBean
import com.zenasi.cryptolist_compose_demo.repository.CryptoRepo
import com.zenasi.cryptolist_compose_demo.utils.ApiResult
import com.zenasi.cryptolist_compose_demo.utils.DataStoreUtils
import com.zenasi.cryptolist_compose_demo.utils.JsonUtils
import com.zenasi.cryptolist_compose_demo.utils.SocketUtils
import com.zenasi.cryptolist_compose_demo.utils.room.RoomDAO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repo: CryptoRepo,
    private val jsonUtils: JsonUtils,
    private val dao: RoomDAO,
    private val socketUtils: SocketUtils,
    private val dataStoreUtils: DataStoreUtils
): BaseViewModel(dataStoreUtils) {

    val cryptoList = mutableStateListOf<AssetBean>()
    var watchList: MutableLiveData<List<AssetEntity>> = MutableLiveData(emptyList())
    val scrollPosition = mutableStateOf(0)
    val scrollVisibleRange = mutableStateOf(0)
    val scrollPositionOffset = mutableStateOf(0)
    val scrollState = mutableStateOf(false)
    val pageIndex = mutableStateOf(CryptoScreen.OverView)

    var cryptoHistoryList: MutableLiveData<List<HistoryBean>> = MutableLiveData(emptyList())

    init{
        getCryptoList()
        socketUtils.liveUpdateListener = { map ->
            viewModelScope.launch {
                if (cryptoList.isEmpty() || scrollState.value || pageIndex.value != CryptoScreen.OverView) return@launch
                (scrollPosition.value..scrollVisibleRange.value).forEach outer@{ pos ->
                    map.keys.forEach inner@{ key ->
                        if (cryptoList[pos].id == key) {
                            queryCrypto(key)
                            return@outer
                        }
                    }
                }
            }
        }
    }

    fun getCryptoList(search: String = "", ids: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val map = mutableMapOf<String, String>()
            // search by asset id (bitcoin) or symbol (BTC)
//            map["search"] = ""
            // query with multiple ids=bitcoin,ethereum,monero
//            map["ids"] = ""
            map["limit"] = "200"
            when (val result = repo.getCryptoList(map)) {
                is ApiResult.OnSuccess -> {
                    val r: List<AssetBean> =
                        jsonUtils.moshiFromJson(jsonUtils.moshi2Json(result.data.data))
                    cryptoList.clear()
                    cryptoList.addAll(r)
                }

                is ApiResult.OnFail -> {

                }

                is ApiResult.OnException -> {

                }
            }
        }
    }

    fun queryWatchList() = viewModelScope.launch(Dispatchers.IO) {
        watchList.postValue(dao.findAll())
    }

    // single query
    private fun queryCrypto(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repo.getCryptoInfoById(id)) {
                is ApiResult.OnSuccess -> {
                    val r: AssetBean? =
                        jsonUtils.moshiFromJsonObject(jsonUtils.moshi2Json(result.data.data))
                    if (cryptoList.isEmpty() || r == null) return@launch
                    for (pos in scrollPosition.value..scrollVisibleRange.value) {
                        if (cryptoList[pos].id == r.id) {
                            val item = cryptoList[pos]
                            when (item.priceState) {
                                PriceState.same -> {
                                    val state =
                                        item.priceUsd.toBigDecimal() <= r.priceUsd.toBigDecimal()
                                    r.priceState =
                                        if (state) PriceState.upGrade else PriceState.downGrade
                                    setUpItemState(pos, r)
                                }

                                else -> {

                                }
                            }
                        }
                    }
                }

                is ApiResult.OnFail -> {

                }

                is ApiResult.OnException -> {

                }
            }
        }
    }

    // interval	required	m1, m5, m15, m30, h1, h2, h6, h12, d1
    fun getChartInfo(id: String?) {
        if (id.isNullOrEmpty()) return
        viewModelScope.launch {
            val map = mutableMapOf<String, String>()
            val range = "m1"
            val startTime = Instant.now().minus(1, ChronoUnit.DAYS).toEpochMilli()
            val endTime = Instant.now().toEpochMilli()
            map["interval"] = range
//            map["start"] = startTime.toString()
//            map["end"] = endTime.toString()

            when (val result = repo.getCryptoHistoryById(id, map)) {
                is ApiResult.OnSuccess -> {
                    cryptoHistoryList.postValue(
                        jsonUtils.moshiFromJson(
                            jsonUtils.moshi2Json(
                                result.data.data
                            )
                        )
                    )
//                    Log.d(TAG, "getChartInfo: ${result.data.data}")
                }

                is ApiResult.OnFail -> {
//                    Log.d(TAG, "getChartInfo: ${result.message}")
                }

                is ApiResult.OnException -> {
//                    Log.d(TAG, "getChartInfo: ${result.e.message}")
                }
            }
        }
    }

    private suspend fun setUpItemState(pos: Int, r: AssetBean) {
        cryptoList[pos].pUsd = r.priceUsd
        cryptoList[pos].p24H = r.changePercent24Hr
        cryptoList[pos].priceState = r.priceState
        delay(500)
        cryptoList[pos].priceState = PriceState.coolDown
        delay(1500)
        cryptoList[pos].priceState = PriceState.same
    }
}