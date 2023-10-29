package com.zenasi.cryptolist_compose_demo.utils

import android.text.TextUtils
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


class ImageUtils {
    companion object {
        fun getCoinImgUrl(coin: String?): String {
            return if (TextUtils.isEmpty(coin)) {
                ""
            } else if (coin == "ustc") {
                "https://assets.coincap.io/assets/icons/ust@2x.png"
            } else {
                "https://assets.coincap.io/assets/icons/${
                    coin
                }@2x.png"
            }
        }
    }


}