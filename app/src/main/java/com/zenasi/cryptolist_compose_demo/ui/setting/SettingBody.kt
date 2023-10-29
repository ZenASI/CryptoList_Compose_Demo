package com.zenasi.cryptolist_compose_demo.ui.setting

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zenasi.cryptolist_compose_demo.R
import com.zenasi.cryptolist_compose_demo.activity.main.MainActivityViewModel
import com.zenasi.cryptolist_compose_demo.ui.component.item.ItemBilateralText
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun SettingBody() {
    val viewModel = viewModel(MainActivityViewModel::class.java)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(horizontal = 5.dp)) {
            val scope = rememberCoroutineScope()
            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val timeStamp = remember {
                mutableStateOf(LocalDateTime.now())
            }
            DisposableEffect(Unit) {
                val handler = Handler(Looper.getMainLooper())
                val runnable = object : Runnable {
                    override fun run() {
                        timeStamp.value = LocalDateTime.now()
                        handler.postDelayed(this, 1000)
                        Log.d("LaunchedEffect", "run: ${timeStamp.value}")
                    }
                }
                handler.postDelayed(runnable, 1000)
                onDispose {
                    handler.removeCallbacks(runnable)
                }
            }

            ItemBilateralText(
                isTitle = false,
                leftText = stringResource(id = R.string.device),
                rightText = Build.MODEL,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            ItemBilateralText(
                isTitle = false,
                leftText = stringResource(id = R.string.time),
                rightText = format.format(timeStamp.value),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(text = stringResource(id = R.string.nightMode))
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = viewModel.nightModeLiveData.collectAsState().value, onCheckedChange = {
                    viewModel.setNightModeValue(it)
                })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(text = stringResource(id = R.string.livePriceUpdate))
                Spacer(modifier = Modifier.weight(1f))
                Switch(checked = viewModel.livePriceUpdate.collectAsState().value, onCheckedChange = {
                    scope.launch {
                        viewModel.livePriceUpdate.emit(it)
                    }
                })
            }
        }
    }
}