package com.zenasi.cryptolist_compose_demo.utils

import android.graphics.Color
import android.text.TextUtils
import java.text.DecimalFormat
import kotlin.math.roundToInt

class PriceUtils {

    companion object {

        private val decimalFormat = DecimalFormat()

        // 3
        private const val K = 1000L

        // 6
        private const val MILLION = 1000000L

        // 9
        private const val BILLION = 1000000000L

        // 12
        private const val TRILLION = 1000000000000L
        val TAG = this::class.java.simpleName
        fun priceCheckLength(price: String?): String {
            return if (price?.isEmpty() == true) {
                ""
            } else {
                val prefix = price?.split(".")?.first()
                if (TextUtils.isEmpty(prefix)) {
                    ""
                } else {
                    if (prefix!!.length > 3) {
                        decimalFormat.format(prefix.toBigDecimal())
                    } else {
                        val suffix = price?.split(".")?.last()
                        val sb = StringBuilder()
                        sb.append(prefix).append(".")
                        if (prefix.length > 1) {
                            sb.append(suffix?.substring(0..2))
                        } else {
                            sb.append(suffix?.substring(0..7))
                        }
                        sb.toString()
                    }
                }
            }
        }

        fun pricePercentIn24HR(percent: String?): String {
            return if (TextUtils.isEmpty(percent)) {
                "0.00%"
            } else {
                val prefix = percent!!.split(".")[0]
                val suffix = percent!!.split(".")[1]
                "${prefix}.${suffix.substring(0..1)}%"
            }
        }

        fun pricePercentRedOrGreen(percent: String?): Int {
            return if (percent?.isEmpty() == true) {
                Color.GRAY
            } else {
                if (percent?.first() == '-') {
                    Color.RED
                } else {
                    Color.GREEN
                }
            }
        }

        fun priceStandardNumber(price: String): String {
            return if (price.isEmpty()) {
                "0"
            } else {
                val prefix = price.split(".").first()
                if (prefix.length > 3) {
                    var temp: Double = prefix.toDouble()
                    when {
                        prefix.length > 12 -> {
//                            Log.d(TAG, "priceStandardNumber <= 12: ${prefix}")
                            temp /= TRILLION
                            "${decimalFormat.format(temp.roundToInt())}t"
                        }
                        prefix.length > 9 -> {
//                            Log.d(TAG, "priceStandardNumber <= 9: ${prefix}")
                            temp /= BILLION

                            "${decimalFormat.format(temp.roundToInt())}b"
                        }
                        prefix.length > 6 -> {
//                            Log.d(TAG, "priceStandardNumber <= 6: ${prefix}")
                            temp /= MILLION
                            "${decimalFormat.format(temp.roundToInt())}m"
                        }
                        else -> {
                            temp /= K
                            "${decimalFormat.format(temp.roundToInt())}k"
                        }
                    }
                } else {
                    val suffix = price.split(".").last()
                    val sb = StringBuilder()
                    sb.append(prefix).append(".").append(suffix.substring(0..1)).toString()
                }
            }
        }
    }
}