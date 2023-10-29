package com.zenasi.cryptolist_compose_demo.ui.component.item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FpsMonitor(modifier: Modifier) {
    var fpsCount by remember { mutableStateOf(0) }
    var fps by remember { mutableStateOf(0) }
    var lastUpdate by remember { mutableStateOf(0L) }
    Text(
        text = "Fps: $fps", modifier = modifier
            .size(60.dp), color = Color.Red
    )

    LaunchedEffect(Unit) {
        while (true) {
            withFrameMillis { ms ->
                fpsCount++
                if (fpsCount == 5) {
                    fps = (5000 / (ms - lastUpdate)).toInt()
                    lastUpdate = ms
                    fpsCount = 0
                }
            }
        }
    }
}