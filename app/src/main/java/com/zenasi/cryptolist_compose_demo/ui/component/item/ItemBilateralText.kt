package com.zenasi.cryptolist_compose_demo.ui.component.item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme

@Composable
fun ItemBilateralText(isTitle: Boolean, leftText: String, rightText: String, modifier: Modifier) {
    val context = LocalContext.current
    androidx.compose.material3.Surface {
        Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = leftText,
                maxLines = 1,
                fontWeight = if (isTitle) FontWeight.Bold else null,
                fontSize = if (isTitle) 18.sp else 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            Text(
                text = rightText,
                maxLines = 1,
                fontWeight = if (isTitle) FontWeight.Bold else null,
                fontSize = if (isTitle) 18.sp else 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.End
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ItemCryptoPreview() {
    CryptoList_Compose_DemoTheme {
        Column() {
            ItemBilateralText(isTitle = true, "123", "123", modifier = Modifier)
            ItemBilateralText(isTitle = false, "123", "123", modifier = Modifier)
        }
    }
}