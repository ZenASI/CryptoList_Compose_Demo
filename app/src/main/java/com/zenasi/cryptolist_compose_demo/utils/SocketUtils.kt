package com.zenasi.cryptolist_compose_demo.utils

import android.util.Log
import okhttp3.*
import okio.ByteString
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SocketUtils @Inject constructor(@Named("Socket") val okHttpClient: OkHttpClient) : WebSocketListener() {

    //wss://ws.coincap.io/prices?assets=ALL
    //wss://ws.coincap.io/trades/binance
    val TAG = this::class.simpleName

    private var webSocket: WebSocket? = null

    var watchListString = "ALL"

    @Inject
    lateinit var jsonUtils: JsonUtils

    var liveUpdateListener: ((Map<String, String>) -> Unit)? = null

    fun connect() {
        Log.d(TAG, "connect: call")
        val request: Request =
            Request.Builder().url("wss://ws.coincap.io/prices?assets=${watchListString}").build()
        webSocket = okHttpClient.newWebSocket(request, this)
    }

    fun disConnect() {
        Log.d(TAG, "disConnect: call")
        webSocket?.cancel()
    }

    fun replaceQuery(data: String?) {
        watchListString = if (data.isNullOrEmpty()) "ALL" else data
        disConnect()
        connect()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        Log.d(TAG, "onClosed: $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(TAG, "onClosing: $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(TAG, "onFailure T: ${t.message}")
        Log.d(TAG, "onFailure T: ${t.localizedMessage}")
        Log.d(TAG, "onFailure T: ${t.printStackTrace()}")
        Log.d(TAG, "onFailure T: ${t.cause}")
        Log.d(TAG, "onFailure response: ${response}")
        super.onFailure(webSocket, t, response)
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
//        Log.d(TAG, "onMessage: ${text}")
//        Log.d(TAG, "onMessage: ${jsonUtils.moshi2Map(text).size}")
        text.isNotEmpty().run {
            val map = jsonUtils.moshi2Map(text)
            if (!map.isNullOrEmpty()){
                Log.d(TAG, "onMessage: ${map.size}")
                liveUpdateListener?.invoke(map)
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        Log.d(TAG, "onMessage: ${bytes}")
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.d(TAG, "onOpen: ${response.message}")
    }
}