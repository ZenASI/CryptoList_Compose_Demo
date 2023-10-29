package com.zenasi.cryptolist_compose_demo.ui.component.tab.tab

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.zenasi.cryptolist_compose_demo.CryptoScreen
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme

@Preview(
    group = "CustomMenuTable",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun CustomMenuTable_Preview_Night() {
    var currentScreen by rememberSaveable { mutableStateOf(CryptoScreen.OverView) }
    CryptoList_Compose_DemoTheme() {
        Surface() {
            CustomMenuTable(
                CryptoScreen.values().toList(),
                { currentScreen = it },
                currentScreen = currentScreen
            )
        }
    }
}

@Preview(
    group = "CustomMenuTable",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun CustomMenuTable_Preview_Light() {
    var currentScreen by rememberSaveable { mutableStateOf(CryptoScreen.OverView) }
    CryptoList_Compose_DemoTheme() {
        Surface() {
            CustomMenuTable(
                CryptoScreen.values().toList(),
                { currentScreen = it },
                currentScreen = currentScreen
            )
        }
    }
}

@Composable
fun CustomMenuTable(
    allScreen: List<CryptoScreen>,
    onTabSelected: (CryptoScreen) -> Unit,
    currentScreen: CryptoScreen
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .selectableGroup()
    ) {
        allScreen.forEachIndexed { index, item ->
            CustomMenuTableItem(
                text = item.name,
                icon = item.icon,
                onClick = { onTabSelected(item) },
                selected = index == currentScreen.ordinal,
                enable = true
            )
        }
    }
}

@Preview(
    group = "CustomChartMenuTable",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun CustomChartMenuTable_Preview_Night() {
    val monthList = arrayListOf(
        "1D", "1W", "1M", "1Y", "ALL"
    )
    var selectMonth by rememberSaveable { mutableStateOf(monthList.first()) }
    CryptoList_Compose_DemoTheme() {
        Surface() {
            CustomChartMenuTable(
                monthList,
                { selectMonth = it },
                selectMonth
            )
        }
    }
}

@Preview(
    group = "CustomChartMenuTable",
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
fun CustomChartMenuTable_Preview_Light() {
    val monthList = arrayListOf(
        "1D", "1W", "1M", "1Y", "ALL"
    )
    var selectMonth by rememberSaveable { mutableStateOf(monthList.first()) }
    CryptoList_Compose_DemoTheme() {
        Surface() {
            CustomChartMenuTable(
                monthList,
                { selectMonth = it },
                selectMonth
            )
        }
    }
}

@Composable
fun CustomChartMenuTable(
    allSelectItem: List<String>,
    onTabSelected: (String) -> Unit,
    currentSelect: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        allSelectItem.forEachIndexed { index, item ->
            CustomChartMenuItem(
                text = item,
                onClick = { onTabSelected(item) },
                selected = allSelectItem[index] == currentSelect,
                enable = true
            )
        }
    }
}