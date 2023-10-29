package com.zenasi.cryptolist_compose_demo.ui.component.tab.tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zenasi.cryptolist_compose_demo.ui.theme.CryptoList_Compose_DemoTheme
import java.util.*

@Composable
fun CustomMenuTableItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    enable: Boolean,
    selected: Boolean
) {
    val animSpec = remember {
        tween<Color>(
            durationMillis = 100,
            easing = LinearEasing,
            delayMillis = 100
        )
    }
    val color = MaterialTheme.colorScheme.onSurface
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = .6f),
        animationSpec = animSpec
    )
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .animateContentSize()
            .height(50.dp)
            .selectable(
                selected = selected,
                enabled = enable,
                onClick = onClick,
                role = Role.Tab,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    bounded = false,
                    radius = Dp.Unspecified,
                    color = Color.Unspecified
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        AnimatedVisibility(visible = selected) {
            Spacer(Modifier.width(12.dp))
            Text(text.uppercase(Locale.getDefault()), color = tabTintColor, maxLines = 1)
        }
    }
}

@Composable
fun CustomChartMenuItem(
    text: String,
    onClick: () -> Unit,
    enable: Boolean = true,
    selected: Boolean
) {
    val color = MaterialTheme.colorScheme.onSurface
    val animSpec = remember {
        tween<Color>(
            durationMillis = 100,
            easing = LinearEasing,
            delayMillis = 100
        )
    }
    val radioTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(.6f),
        animSpec
    )
    CryptoList_Compose_DemoTheme {
        Text(
            text = text, modifier = Modifier
                .wrapContentHeight()
                .clip(RoundedCornerShape(5.dp))
                .selectable(
                    selected = selected,
                    enabled = enable,
                    onClick = onClick,
                    role = Role.RadioButton,
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(
                        bounded = false,
                        radius = Dp.Unspecified,
                        color = Color.Unspecified
                    )
                )
                .padding(vertical = 6.dp, horizontal = 16.dp),
            color = radioTintColor,
            maxLines = 1
        )
    }
}