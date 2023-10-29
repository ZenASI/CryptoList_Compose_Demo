package com.zenasi.cryptolist_compose_demo.activity

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repo: CryptoRepo,
    private val jsonUtils: JsonUtils,
    private val dao: RoomDAO,
    private val socketUtils: SocketUtils,
    private val dataStoreUtils: DataStoreUtils
): ViewModel() {

    val cryptoList = mutableStateListOf<AssetBean>()

    init{
        getCryptoList()
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
}