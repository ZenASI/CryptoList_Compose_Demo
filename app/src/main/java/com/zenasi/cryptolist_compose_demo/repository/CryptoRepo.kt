package com.zenasi.cryptolist_compose_demo.repository


import com.zenasi.cryptolist_compose_demo.model.BaseResponse
import com.zenasi.cryptolist_compose_demo.module.AppApiModule
import com.zenasi.cryptolist_compose_demo.utils.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepo @Inject constructor(private val appApiModule: AppApiModule) : BaseRepo() {

    val TAG = this::class.java.simpleName

    suspend fun getCryptoList(map: Map<String, String>): ApiResult<BaseResponse> =
        handleApiResponse { appApiModule.asset(map) }

    suspend fun getCryptoInfoById(id: String): ApiResult<BaseResponse> =
        handleApiResponse { appApiModule.assetById(id) }

    suspend fun getCryptoHistoryById(
        id: String,
        map: Map<String, String>
    ): ApiResult<BaseResponse> =
        handleApiResponse { appApiModule.assetByIdForHistory(id, map) }

    suspend fun getCryptoMarketsById(
        id: String,
        map: Map<String, String>
    ): ApiResult<BaseResponse> =
        handleApiResponse { appApiModule.assetByIdForMarket(id, map) }

}