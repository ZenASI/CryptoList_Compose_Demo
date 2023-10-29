package com.zenasi.cryptolist_compose_demo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.ViewModelProvider
import com.zenasi.cryptolist_compose_demo.App
import com.zenasi.cryptolist_compose_demo.ui.overview.OverViewBody
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
            CryptoList_Compose_DemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {

                    }) { paddingValues ->
                        Box(modifier = Modifier.padding(paddingValues)) {
                            OverViewBody()
                        }
                    }
                }
            }
        }
    }
}