package com.zenasi.cryptolist_compose_demo.activity.info

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zenasi.cryptolist_compose_demo.Def
import com.zenasi.cryptolist_compose_demo.model.asset.AssetBean
import com.zenasi.cryptolist_compose_demo.ui.component.item.ItemBilateralText
import com.zenasi.cryptolist_compose_demo.ui.component.item.ItemCryptoLinearChart
import com.zenasi.cryptolist_compose_demo.ui.component.tab.tab.CustomChartMenuTable
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme
import com.zenasi.cryptolist_compose_demo.utils.ImageUtils
import com.zenasi.cryptolist_compose_demo.utils.PriceUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class CryptoInfoActivity : ComponentActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[CryptoInfoViewModel::class.java]
    }

    private val assetBean: AssetBean by lazy {
        intent.getParcelableExtra<AssetBean>(Def.CryptoId.name) as AssetBean
    }

    val monthList = arrayListOf(
        "1D", "1W", "1M", "1Y", "ALL"
    )

    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.assBean2Map(assetBean)
        viewModel.getMarketInfo(assetBean.id)
        viewModel.getChartInfo(assetBean.id, monthList.first())
        setContent {
            CryptoList_Compose_DemoTheme(
//                darkTheme = viewModel.nightModeLiveData.collectAsState(
//                    initial = false
//                ).value
            ) {
                val menuState = remember { mutableStateOf(false) }
                val urlHandler = LocalUriHandler.current
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        Row(
                            modifier = Modifier
                                .animateContentSize()
                                .padding(vertical = 10.dp, horizontal = 16.dp)
                                .fillMaxWidth()
                                .height(50.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // top bar
                            Image(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable(enabled = true, onClick = { finish() }),
                                contentScale = ContentScale.Inside,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                            )
                            Row {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current).data(
                                        ImageUtils.getCoinImgUrl(
                                            assetBean.symbol.lowercase(
                                                Locale.ROOT
                                            )
                                        )
                                    ).crossfade(true).build(), contentDescription = ""
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = assetBean.symbol.uppercase(Locale.getDefault()),
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Image(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(50.dp)
                                    .clickable(enabled = true, onClick = {
                                        // change menu state
                                        menuState.value = true
                                    }),
                                contentScale = ContentScale.Inside,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                            )
                        }
                    }) { paddingValues ->
                        // content view
                        val map = viewModel.assBeanMap.observeAsState().value
                        val list = viewModel.cryptoMarketList.observeAsState().value
                        val historyList = viewModel.cryptoHistoryList
                        val listState = rememberLazyListState()
                        val coroutineScope = rememberCoroutineScope()
                        var selectMonth by rememberSaveable { mutableStateOf(monthList.first()) }

                        LaunchedEffect(key1 = selectMonth) {
                            viewModel.getChartInfo(assetBean.id, selectMonth)
                        }

                        Column(
                            modifier = Modifier.padding(paddingValues),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // priceUsd
                            Text(
                                text = "$${PriceUtils.priceCheckLength(assetBean.priceUsd)}",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.headlineLarge,
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // TODO: 帶修正數值
                                Image(
                                    modifier = Modifier.rotate(if (assetBean.changePercent24Hr.first() == '-') -90f else 90f),
                                    imageVector = Icons.Filled.ArrowLeft,
                                    contentDescription = "arrow",
                                    colorFilter = ColorFilter.tint(
                                        Color(
                                            PriceUtils.pricePercentRedOrGreen(assetBean.changePercent24Hr)
                                        )
                                    )
                                )
                                Text(
                                    text = PriceUtils.pricePercentIn24HR(assetBean.changePercent24Hr),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color(PriceUtils.pricePercentRedOrGreen(assetBean.changePercent24Hr))
                                )
                            }

                            // chart
                            ItemCryptoLinearChart(
                                labelString = assetBean.name,
                                historyList = historyList,
                            )
                            // radio
                            CustomChartMenuTable(
                                allSelectItem = monthList, onTabSelected = {
                                    selectMonth = it
                                    coroutineScope.launch {
                                        viewModel.getChartInfo(assetBean.id, selectMonth)
                                    }
                                }, selectMonth
                            )
                            LazyColumn(state = listState) {
                                // coin state
                                stickyHeader {
                                    if (!map.isNullOrEmpty()) {
                                        ItemBilateralText(
                                            isTitle = true,
                                            leftText = "CoinState",
                                            rightText = "",
                                            modifier = Modifier.padding(horizontal = 5.dp)
                                        )
                                    }
                                }
                                map?.forEach { it ->
                                    item {
                                        ItemBilateralText(
                                            isTitle = false,
                                            leftText = it.key,
                                            rightText = it.value,
                                            modifier = Modifier.padding(horizontal = 5.dp)
                                        )
                                    }
                                }
                                // coin state end
                                stickyHeader {
                                    if (!list.isNullOrEmpty()) {
                                        ItemBilateralText(
                                            isTitle = true,
                                            leftText = "Market",
                                            rightText = "",
                                            modifier = Modifier.padding(horizontal = 5.dp)
                                        )
                                    }
                                }

                                if (!list.isNullOrEmpty()) {
                                    // coin market
                                    items(items = list) { item ->
                                        ItemBilateralText(
                                            isTitle = false,
                                            leftText = item.exchangeId,
                                            rightText = PriceUtils.priceStandardNumber(item.volumeUsd24Hr),
                                            modifier = Modifier.padding(horizontal = 5.dp)
                                        )
                                    }
                                    // scroll top text
                                    item {
                                        Text(
                                            text = "Back To Top",
                                            Modifier
                                                .fillMaxWidth()
                                                .clickable(enabled = true, onClick = {
                                                    coroutineScope.launch {
                                                        listState.animateScrollToItem(0)
                                                    }
                                                })
                                                .padding(vertical = 10.dp),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
                Surface {
                    if (menuState.value) {
                        Dialog(
                            onDismissRequest = {
                                menuState.value = false
                            }, properties = DialogProperties(
                                dismissOnBackPress = true,
                                dismissOnClickOutside = true,
                                securePolicy = SecureFlagPolicy.Inherit,
                                usePlatformDefaultWidth = false
                            )
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .wrapContentHeight()
                                    .padding(0.dp), shape = RoundedCornerShape(8.dp)
                            ) {
                                TextButton(
                                    onClick = {
                                        menuState.value = false
                                        viewModel.insertWatchList(assetBean = assetBean)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp),
                                    shape = RoundedCornerShape(
                                        topStart = 8.dp,
                                        topEnd = 8.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                ) {
                                    Text(text = "Add WatchList")
                                }
                                Divider(
                                    modifier = Modifier
                                        .height(1.dp)
                                        .padding(0.dp)
                                )
                                TextButton(
                                    onClick = {
                                        menuState.value = false
                                        urlHandler.openUri(assetBean.explorer)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(45.dp),
                                    shape = RoundedCornerShape(
                                        topStart = 0.dp,
                                        topEnd = 0.dp,
                                        bottomStart = 8.dp,
                                        bottomEnd = 8.dp
                                    )
                                ) {
                                    Text(text = "Explorer")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CryptoChartPreView() {

}
