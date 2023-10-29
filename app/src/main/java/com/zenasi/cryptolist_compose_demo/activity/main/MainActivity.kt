package com.zenasi.cryptolist_compose_demo.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.zenasi.cryptolist_compose_demo.App
import com.zenasi.cryptolist_compose_demo.CryptoScreen
import com.zenasi.cryptolist_compose_demo.ui.component.tab.tab.CustomMenuTable
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    @Inject
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val allScreen = CryptoScreen.values().toList()
            var currentScreen by rememberSaveable { mutableStateOf(CryptoScreen.OverView) }
            CryptoList_Compose_DemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        CustomMenuTable(
                            allScreen,
                            onTabSelected = { screen ->
                                currentScreen = screen
                                viewModel.pageIndex.value = screen
                                // 暫時先註解
//                                if (screen == CryptoScreen.OverView)
//                                    viewModel.getCryptoList()
                            },
                            currentScreen
                        )
                    }) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            Crossfade(targetState = currentScreen, label = "") { it ->
                                it.body.invoke()
//                                when (it) {
//                                    CryptoScreen.OverView -> OverViewBody()
//                                    CryptoScreen.CollectView -> OverViewBody()
//                                    CryptoScreen.SettingView -> OverViewBody()
//                                }
                            }
                        }
                    }
                }
            }
        }
    }
}