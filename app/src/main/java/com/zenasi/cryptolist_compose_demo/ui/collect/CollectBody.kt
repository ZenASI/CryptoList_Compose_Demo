package com.zenasi.cryptolist_compose_demo.ui.collect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zenasi.cryptolist_compose_demo.activity.main.MainActivityViewModel
import com.zenasi.cryptolist_compose_demo.model.AssetEntity
import com.zenasi.cryptolist_compose_demo.ui.component.item.ItemWatch


@Composable
fun CollectBody() {
    val viewModel = viewModel(MainActivityViewModel::class.java)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.queryWatchList()
        val watchList = viewModel.watchList.observeAsState().value
        var selectId by remember { mutableStateOf("") }

        // chart
        val historyList = viewModel.cryptoHistoryList.observeAsState().value
        val assetList = viewModel.cryptoList

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            contentPadding = PaddingValues(3.dp),
        ) {
            items(items = watchList!!, key = { item: AssetEntity -> item.id }) { item ->
                ItemWatch(assetEntity = item,
                    historyList = historyList,
                    assetList = assetList,
                    isSelect = selectId == item.id,
                    onClick = { selectId = it },
                    onRequest = { viewModel.getChartInfo(it) })
            }
        }
    }
}