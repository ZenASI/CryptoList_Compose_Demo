package com.zenasi.cryptolist_compose_demo.ui.component.item

import android.content.Context
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.zenasi.cryptolist_compose_demo.R
import com.zenasi.cryptolist_compose_demo.model.asset.HistoryBean
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme

@Preview(group = "ItemCryptoLinearChart", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ItemCryptoLinearChart_Preview_Night() {
    CryptoList_Compose_DemoTheme() {
        Surface() {
            ItemCryptoLinearChart(null, null)
        }
    }
}

@Preview(group = "ItemCryptoLinearChart", uiMode = UI_MODE_NIGHT_NO)
@Composable
fun ItemCryptoLinearChart_Preview_Light() {
    CryptoList_Compose_DemoTheme() {
        Surface() {
            ItemCryptoLinearChart(null, null)
        }
    }
}

@Composable
fun ItemCryptoLinearChart(labelString: String?, historyList: List<HistoryBean>?) {
    Log.d("ItemCryptoLinearChart", "init call: ${historyList?.size}")
    val color = MaterialTheme.colorScheme.onSurface.toArgb()
    AndroidView(factory = { context: Context ->
        Log.d("ItemCryptoLinearChart", "androidView content call: ")
        LineChart(context).apply {
            // init
            setDrawGridBackground(false)
            setNoDataText("No Data")
            description.text = ""
            setDrawBorders(false)
            setPinchZoom(false)

            setScaleEnabled(false)
            setTouchEnabled(false)
            isDragEnabled = false
            isHighlightPerTapEnabled = false
            setViewPortOffsets(0f, 0f, 0f, 0f)

            // basic set
            // x
            val xAxis = this.xAxis
            xAxis.setDrawAxisLine(false)
            xAxis.isEnabled = false
            // x end =========

            // left y
            val leftAxis = this.axisLeft
            leftAxis.isEnabled = false
            leftAxis.setDrawAxisLine(false)
            // left y end =========

            // right y
            val rightAxis = this.axisRight
            rightAxis.isEnabled = false
            rightAxis.setDrawAxisLine(false)
            // right y end =========

            // api data add
            this.data = prepareLineDataList(labelString, historyList, context)
            // label
            this.legend.textColor = color
        }
    }, modifier = Modifier.aspectRatio(1.3f), update = { view ->
        view.apply {
            Log.d("ItemCryptoLinearChart", "androidView update call: ")
            data = prepareLineDataList(labelString, historyList, context)
            legend.textColor = color
            notifyDataSetChanged() // let the chart know it's data changed
            invalidate() // refresh
        }
    })
}


private fun prepareLineDataList(
    label: String?,
    priceList: List<HistoryBean>?,
    context: Context
): LineData {
    if (priceList.isNullOrEmpty()) return LineData()
    val yEntry = mutableListOf<Entry>()
    for (item in priceList) {
        yEntry.add(
            Entry(
                item.time.toFloat(), item.priceUsd.toFloat()
            )
        )
    }
    // data set
    val dataSet = LineDataSet(yEntry, "${label}")
    dataSet.setDrawFilled(true)
    val firstItem = priceList.first()
    val lastItem = priceList.last()
    if (firstItem.priceUsd.toFloat() < lastItem.priceUsd.toFloat()) {
        dataSet.fillDrawable = ContextCompat.getDrawable(context, R.drawable.green_gradient)
        dataSet.color = android.graphics.Color.GREEN
    } else {
        dataSet.fillDrawable = ContextCompat.getDrawable(context, R.drawable.red_gradient)
        dataSet.color = android.graphics.Color.RED
    }
    dataSet.setDrawCircles(false)
    return LineData(dataSet)
}