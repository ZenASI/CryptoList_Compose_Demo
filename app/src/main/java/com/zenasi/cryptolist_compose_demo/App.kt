package com.zenasi.cryptolist_compose_demo


import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.zenasi.cryptolist_compose_demo.utils.SocketUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class App : Application(), LifecycleEventObserver {

    private val TAG = this::class.simpleName

    @Inject
    lateinit var socketUtils: SocketUtils

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                Log.d(TAG, "onStateChanged: ON_RESUME")
//                socketUtils.connect()
            }
            Lifecycle.Event.ON_PAUSE -> {
                Log.d(TAG, "onStateChanged: ON_PAUSE")
//                socketUtils.disConnect()
            }
            else ->{

            }
        }
    }

    fun disConnectSocket() = socketUtils?.disConnect()

    fun connectSocket() = socketUtils?.connect()
}