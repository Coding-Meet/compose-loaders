package com.meet.compose.loaders.ui.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.presets.PixelPresets
import com.meet.compose.loaders.ui.common.ColorPickerDialog
import com.meet.compose.loaders.ui.common.ColorSwatch
import com.meet.compose.loaders.ui.common.DynamicCustomColorSwatch
import com.meet.compose.loaders.ui.gallery.components.FilterChip
import com.meet.compose.loaders.ui.gallery.components.PresetGalleryCard

enum class ColorPickerTarget { ON_COLOR, OFF_COLOR, CANVAS_BG }

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GalleryScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onSelectPreset: (PixelPreset) -> Unit
) {
    val defaultOnColor = Color(0xFF6366F1)
    val outlineColor = MaterialTheme.colorScheme.outline
    val defaultCanvasBg = MaterialTheme.colorScheme.background

    var customActiveColor by remember { mutableStateOf<Color?>(null) }
    var selectedActiveColor by remember { mutableStateOf<Color>(defaultOnColor) }

    var customInactiveColor by remember { mutableStateOf<Color?>(null) }
    var selectedInactiveColor by remember { mutableStateOf<Color>(outlineColor) }

    var customCanvasBgColor by remember { mutableStateOf<Color?>(null) }
    var selectedCanvasBgColor by remember { mutableStateOf<Color>(defaultCanvasBg) }

    var globalSpeed by remember { mutableStateOf(1.0f) }
    var activeColorTarget by remember { mutableStateOf<ColorPickerTarget?>(null) }

    val presets = PixelPresets.all

    // Color Picker Dialog
    activeColorTarget?.let { target ->
        val initial = when (target) {
            ColorPickerTarget.ON_COLOR -> customActiveColor ?: Color(0xFFEC4899)
            ColorPickerTarget.OFF_COLOR -> customInactiveColor ?: Color(0xFF27272A)
            ColorPickerTarget.CANVAS_BG -> customCanvasBgColor ?: Color(0xFF0F172A)
        }
        val title = when (target) {
            ColorPickerTarget.ON_COLOR -> "Pick Custom ON Color"
            ColorPickerTarget.OFF_COLOR -> "Pick Custom OFF Color"
            ColorPickerTarget.CANVAS_BG -> "Pick Custom Canvas BG"
        }
        ColorPickerDialog(
            initialColor = initial,
            title = title,
            onColorSelected = { selected ->
                when (target) {
                    ColorPickerTarget.ON_COLOR -> {
                        customActiveColor = selected
                        selectedActiveColor = selected
                    }
                    ColorPickerTarget.OFF_COLOR -> {
                        customInactiveColor = selected
                        selectedInactiveColor = selected
                    }
                    ColorPickerTarget.CANVAS_BG -> {
                        customCanvasBgColor = selected
                        selectedCanvasBgColor = selected
                    }
                }
            },
            onDismissRequest = { activeColorTarget = null }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        // Header Row (Safe Top Inset)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Pixel Loaders",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Handcrafted Pixel Loaders for Compose Multiplatform",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            }

            IconButton(
                onClick = onToggleTheme,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                    contentDescription = "Toggle Theme",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Responsive Live Configuration Bar
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, outlineColor, RoundedCornerShape(14.dp))
                .padding(14.dp)
        ) {
            val isCompact = maxWidth < 600.dp

            if (isCompact) {
                // Mobile Portrait Layout: Stacked Rows
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // ON Color
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("ON:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            val staticOnColors = listOf(Color(0xFF6366F1), Color(0xFF10B981), Color(0xFFF59E0B))
                            staticOnColors.forEach { color ->
                                ColorSwatch(color = color, isSelected = selectedActiveColor == color, outlineColor = outlineColor, size = 22.dp) { selectedActiveColor = color }
                            }
                            DynamicCustomColorSwatch(
                                customColor = customActiveColor,
                                isSelected = selectedActiveColor == customActiveColor && customActiveColor != null,
                                outlineColor = outlineColor,
                                size = 22.dp,
                                onSelect = { if (customActiveColor != null) selectedActiveColor = customActiveColor!! else activeColorTarget = ColorPickerTarget.ON_COLOR },
                                onEdit = { activeColorTarget = ColorPickerTarget.ON_COLOR }
                            )
                        }

                        // OFF Color
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("OFF:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            val staticOffColors = listOf(outlineColor, Color(0xFF18181B), Color(0xFFE4E4E7))
                            staticOffColors.forEach { color ->
                                ColorSwatch(color = color, isSelected = selectedInactiveColor == color, outlineColor = outlineColor, size = 22.dp) { selectedInactiveColor = color }
                            }
                            DynamicCustomColorSwatch(
                                customColor = customInactiveColor,
                                isSelected = selectedInactiveColor == customInactiveColor && customInactiveColor != null,
                                outlineColor = outlineColor,
                                size = 22.dp,
                                onSelect = { if (customInactiveColor != null) selectedInactiveColor = customInactiveColor!! else activeColorTarget = ColorPickerTarget.OFF_COLOR },
                                onEdit = { activeColorTarget = ColorPickerTarget.OFF_COLOR }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Canvas BG
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("BG:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            val staticBgColors = listOf(defaultCanvasBg, Color(0xFF000000), Color(0xFF0F172A))
                            staticBgColors.forEach { color ->
                                ColorSwatch(color = color, isSelected = selectedCanvasBgColor == color, outlineColor = outlineColor, size = 22.dp) { selectedCanvasBgColor = color }
                            }
                            DynamicCustomColorSwatch(
                                customColor = customCanvasBgColor,
                                isSelected = selectedCanvasBgColor == customCanvasBgColor && customCanvasBgColor != null,
                                outlineColor = outlineColor,
                                size = 22.dp,
                                onSelect = { if (customCanvasBgColor != null) selectedCanvasBgColor = customCanvasBgColor!! else activeColorTarget = ColorPickerTarget.CANVAS_BG },
                                onEdit = { activeColorTarget = ColorPickerTarget.CANVAS_BG }
                            )
                        }

                        // Speed
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text("Speed:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { speed ->
                                FilterChip(label = "${speed}x", isSelected = globalSpeed == speed) { globalSpeed = speed }
                            }
                        }
                    }
                }
            } else {
                // Wide Desktop / Tablet Layout
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("ON Color:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            listOf(Color(0xFF6366F1), Color(0xFF10B981), Color(0xFFF59E0B)).forEach { color ->
                                ColorSwatch(color = color, isSelected = selectedActiveColor == color, outlineColor = outlineColor, size = 22.dp) { selectedActiveColor = color }
                            }
                            DynamicCustomColorSwatch(
                                customColor = customActiveColor,
                                isSelected = selectedActiveColor == customActiveColor && customActiveColor != null,
                                outlineColor = outlineColor,
                                size = 22.dp,
                                onSelect = { if (customActiveColor != null) selectedActiveColor = customActiveColor!! else activeColorTarget = ColorPickerTarget.ON_COLOR },
                                onEdit = { activeColorTarget = ColorPickerTarget.ON_COLOR }
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("OFF Color:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            listOf(outlineColor, Color(0xFF18181B), Color(0xFFE4E4E7)).forEach { color ->
                                ColorSwatch(color = color, isSelected = selectedInactiveColor == color, outlineColor = outlineColor, size = 22.dp) { selectedInactiveColor = color }
                            }
                            DynamicCustomColorSwatch(
                                customColor = customInactiveColor,
                                isSelected = selectedInactiveColor == customInactiveColor && customInactiveColor != null,
                                outlineColor = outlineColor,
                                size = 22.dp,
                                onSelect = { if (customInactiveColor != null) selectedInactiveColor = customInactiveColor!! else activeColorTarget = ColorPickerTarget.OFF_COLOR },
                                onEdit = { activeColorTarget = ColorPickerTarget.OFF_COLOR }
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Canvas BG:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            listOf(defaultCanvasBg, Color(0xFF000000), Color(0xFF0F172A)).forEach { color ->
                                ColorSwatch(color = color, isSelected = selectedCanvasBgColor == color, outlineColor = outlineColor, size = 22.dp) { selectedCanvasBgColor = color }
                            }
                            DynamicCustomColorSwatch(
                                customColor = customCanvasBgColor,
                                isSelected = selectedCanvasBgColor == customCanvasBgColor && customCanvasBgColor != null,
                                outlineColor = outlineColor,
                                size = 22.dp,
                                onSelect = { if (customCanvasBgColor != null) selectedCanvasBgColor = customCanvasBgColor!! else activeColorTarget = ColorPickerTarget.CANVAS_BG },
                                onEdit = { activeColorTarget = ColorPickerTarget.CANVAS_BG }
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Speed:", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { speed ->
                            FilterChip(label = "${speed}x", isSelected = globalSpeed == speed) { globalSpeed = speed }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Preset Grid
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(presets) { preset ->
                PresetGalleryCard(
                    preset = preset,
                    activeColor = selectedActiveColor,
                    inactiveColor = selectedInactiveColor,
                    canvasBgColor = selectedCanvasBgColor,
                    speedMultiplier = globalSpeed,
                    onClick = { onSelectPreset(preset) }
                )
            }
        }
    }
}
