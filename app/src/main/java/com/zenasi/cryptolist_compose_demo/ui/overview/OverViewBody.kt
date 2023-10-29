package com.zenasi.cryptolist_compose_demo.ui.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zenasi.cryptolist_compose_demo.activity.main.MainActivityViewModel
import com.zenasi.cryptolist_compose_demo.model.asset.AssetBean
import com.zenasi.cryptolist_compose_demo.ui.component.item.ItemCrypto


@Composable
fun OverViewBody() {
    val viewModel = viewModel(MainActivityViewModel::class.java)
    val configuration = LocalConfiguration.current
    val list = viewModel.cryptoList
//    val listState = rememberLazyListState(
//        viewModel.scrollPosition.value, viewModel.scrollPositionOffset.value
//    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val limitRange = ((configuration.screenHeightDp - 50) / 50) - 1
        // remember scroll position
//        LaunchedEffect(key1 = listState.isScrollInProgress, block = {
//            viewModel.scrollState.value = listState.isScrollInProgress
//            if (!listState.isScrollInProgress) {
//                viewModel.scrollPosition.value = listState.firstVisibleItemIndex
//                viewModel.scrollPositionOffset.value = listState.firstVisibleItemScrollOffset
//                viewModel.scrollVisibleRange.value =
//                    if (listState.firstVisibleItemIndex == 0) limitRange else min(
//                        listState.firstVisibleItemIndex + limitRange, list.size - 1
//                    )
//            }
//        })
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            contentPadding = PaddingValues(3.dp),
//            state = listState,
        ) {
            items(
                items = list,
                key = { item: AssetBean -> item.id },
                contentType = { item: AssetBean -> item.id }) { item ->
                ItemCrypto(assetBean = item)
            }
        }
//        Box(modifier = Modifier.align(Alignment.TopCenter)) {
//            FpsMonitor(modifier = Modifier)
//        }
    }
}