package com.meet.compose.loaders.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun DynamicCustomColorSwatch(
    customColor: Color?,
    isSelected: Boolean,
    outlineColor: Color = MaterialTheme.colorScheme.outline,
    size: Dp = 28.dp,
    onSelect: () -> Unit,
    onEdit: () -> Unit
) {
    val iconSize = if (size < 25.dp) 12.dp else 16.dp
    val editIconSize = if (size < 25.dp) 10.dp else 12.dp

    if (customColor == null) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, outlineColor, CircleShape)
                .clickable(onClick = onEdit),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Custom Color",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(iconSize)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(customColor)
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else outlineColor,
                    shape = CircleShape
                )
                .clickable(onClick = onSelect),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = onEdit)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Custom Color",
                    tint = if (customColor == Color.White) Color.Black.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(editIconSize)
                )
            }
        }
    }
}
