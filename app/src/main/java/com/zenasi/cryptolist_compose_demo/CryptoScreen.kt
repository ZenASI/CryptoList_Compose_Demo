package com.zenasi.cryptolist_compose_demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.zenasi.cryptolist_compose_demo.ui.overview.OverViewBody

enum class CryptoScreen(val icon: ImageVector, val body: @Composable () -> Unit) {
    OverView(icon = Icons.Filled.ListAlt, body = { OverViewBody() }),
    CollectView(icon = Icons.Filled.Collections, body = { OverViewBody() }),
    SettingView(icon = Icons.Filled.Settings, body = { OverViewBody() }),
}