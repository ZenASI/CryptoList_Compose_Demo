package com.zenasi.cryptolist_compose_demo.ui.component.item

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zenasi.cryptolist_compose_demo.Def
import com.zenasi.cryptolist_compose_demo.PriceState
import com.zenasi.cryptolist_compose_demo.R
import com.zenasi.cryptolist_compose_demo.model.AssetEntity
import com.zenasi.cryptolist_compose_demo.model.asset.AssetBean
import com.zenasi.cryptolist_compose_demo.model.asset.HistoryBean
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme
import com.zenasi.cryptolist_compose_demo.utils.ImageUtils
import com.zenasi.cryptolist_compose_demo.utils.PriceUtils

import kotlinx.coroutines.*

@Preview(group = "ItemCrypto", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ItemCrypto_Preview_Night() {
    CryptoList_Compose_DemoTheme {
        ItemCrypto(AssetBean().getFakeAsset())
    }
}

@Preview(group = "ItemCrypto", uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun ItemCrypto_Preview_Light() {
    CryptoList_Compose_DemoTheme {
        ItemCrypto(AssetBean().getFakeAsset())
    }
}

@Composable
fun ItemCrypto(assetBean: AssetBean?) {
    val context = LocalContext.current
    val transition =
        updateTransition(targetState = assetBean?.priceState, label = "transition")
    val colorState by transition.animateColor(label = "colorState") {
        when (it) {
            PriceState.upGrade -> Color.Green.copy(alpha = .4f)
            PriceState.downGrade -> Color.Red.copy(alpha = .4f)
            else -> MaterialTheme.colorScheme.background
        }
    }

    val imagePath = rememberSaveable {
        ImageUtils.getCoinImgUrl(assetBean?.symbol?.lowercase())
    }
    Box(modifier = Modifier.clip(RoundedCornerShape(5.dp))) {
        Row(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .clickable(enabled = true, onClick = {
//                    val intent = Intent(context, CryptoInfoActivity::class.java)
//                    intent.putExtra(Def.CryptoId.name, assetBean)
//                    context.startActivity(intent)
                })
                .drawBehind {
                    drawRect(color = colorState)
                }
        ) {
            // 虛擬貨幣圖icon
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.CenterVertically)
            ) {
                ItemCryptoImage(path = imagePath)
            }
            // 虛擬貨幣名稱
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 8.dp)
            ) {
                Text(text = "${assetBean?.symbol}", modifier = Modifier.align(Alignment.TopStart))
                Text(
                    text = "${assetBean?.name}",
                    modifier = Modifier.align(Alignment.BottomStart),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            // 價格
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 10.dp)
            ) {
                Text(
                    text = PriceUtils.priceCheckLength(assetBean?.pUsd),
                    modifier = Modifier.align(Alignment.TopEnd)
                )
                Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                    Image(
                        modifier = Modifier.rotate(if (assetBean?.p24H?.first() == '-') -90f else 90f),
                        imageVector = Icons.Filled.ArrowLeft,
                        contentDescription = "arrow",
                        colorFilter = ColorFilter.tint(
                            Color(
                                PriceUtils.pricePercentRedOrGreen(assetBean?.p24H)
                            )
                        )
                    )
                    Text(
                        text = PriceUtils.pricePercentIn24HR(assetBean?.p24H),
                        color = Color(PriceUtils.pricePercentRedOrGreen(assetBean?.p24H))
                    )
                }
            }
        }
    }
}

@Preview(group = "ItemWatch", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ItemWatch_Preview_Night() {
    var selectId by remember { mutableStateOf("") }
    val fakeAsset = AssetEntity().getFakeAsset()
    CryptoList_Compose_DemoTheme() {
        ItemWatch(fakeAsset, null, null, selectId == fakeAsset.id, { selectId = it }, {})
    }
}

@Preview(group = "ItemWatch", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ItemWatch_Preview_Light() {
    var selectId by remember { mutableStateOf("") }
    val fakeAsset = AssetEntity().getFakeAsset()
    CryptoList_Compose_DemoTheme() {
        ItemWatch(fakeAsset, null, null, selectId == fakeAsset.id, { selectId = it }, {})
    }
}

@Composable
fun ItemWatch(
    assetEntity: AssetEntity?,
    historyList: List<HistoryBean>?,
    assetList: List<AssetBean>?,
    isSelect: Boolean,
    onClick: (id: String) -> Unit,
    onRequest: (id: String) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(5.dp),
        shadowElevation = 8.dp,
    ) {
        val context = LocalContext.current
        Column {
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()
                    .animateContentSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 虛擬貨幣圖icon
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(ImageUtils.getCoinImgUrl(assetEntity?.symbol!!.lowercase()))
                        .crossfade(true).build(),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .width(40.dp)
                        .height(40.dp)
                        .align(Alignment.CenterVertically),
                    placeholder = null,
                    contentScale = ContentScale.Crop,
                )
                // 虛擬貨幣名稱
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "${assetEntity?.symbol}",
                        modifier = Modifier.align(Alignment.TopStart)
                    )
                    Text(
                        text = "${assetEntity?.name}",
                        modifier = Modifier.align(Alignment.BottomStart),
                        color = Color.Gray
                    )
                }
                // space let arrow to end
                val foldAnim = animateFloatAsState(targetValue = if (isSelect) 90f else 0f)
                Spacer(modifier = Modifier.weight(1f))
                // fold switch button
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Fold",
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(end = 10.dp)
                        .clickable(enabled = true, onClick = {
                            val clickId = if (isSelect) "" else assetEntity.id
                            onClick.invoke(clickId)
                            if (clickId.isNotEmpty()) {
                                onRequest.invoke(assetEntity.id)
                            }
                        })
                        .rotate(foldAnim.value)
                )
            }

            AnimatedVisibility(
                visible = isSelect,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // chart
                    ItemCryptoLinearChart(assetEntity?.name, historyList)
                    TextButton(
                        onClick = {
                            if (assetList.isNullOrEmpty()) return@TextButton
                            for (item in assetList) {
                                if (item.id == assetEntity?.id) {
//                                    val intent = Intent(context, CryptoInfoActivity::class.java)
//                                    intent.putExtra(Def.CryptoId.name, item)
//                                    context.startActivity(intent)
                                    return@TextButton
                                }
                            }
                        }, contentPadding = PaddingValues(horizontal = 20.dp)
                    ) {
                        Text(text = stringResource(id = R.string.more))
                    }
                }
            }
        }
    }
}