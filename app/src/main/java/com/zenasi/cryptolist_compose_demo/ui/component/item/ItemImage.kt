package com.zenasi.cryptolist_compose_demo.ui.component.item

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Stable
@Composable
fun ItemCryptoImage(path:String?){
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(path)
            .crossfade(true)
            .build(),
        contentDescription = "",
        modifier = Modifier
            .padding(start = 10.dp)
            .width(40.dp)
            .height(40.dp),
        placeholder = null,
        contentScale = ContentScale.Crop,
    )
}