package com.meet.compose.loaders.ui.studio

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meet.compose.loaders.PixelLoader
import com.meet.compose.loaders.export.CodeExporter
import com.meet.compose.loaders.export.SvgExporter
import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.ui.common.ColorPickerDialog
import com.meet.compose.loaders.ui.common.ColorSwatch
import com.meet.compose.loaders.ui.common.DynamicCustomColorSwatch
import com.meet.compose.loaders.ui.studio.components.GridSizeOptionButton
import com.meet.compose.loaders.ui.studio.components.OptionBadge

enum class ExportFormat { KOTLIN_CODE, SVG_VECTOR }
enum class ColorPickerTarget { ON_COLOR, OFF_COLOR, CANVAS_BG }

private fun Color.toHexString(): String {
    val r = (red * 255).toInt().coerceIn(0, 255).toString(16).padStart(2, '0')
    val g = (green * 255).toInt().coerceIn(0, 255).toString(16).padStart(2, '0')
    val b = (blue * 255).toInt().coerceIn(0, 255).toString(16).padStart(2, '0')
    return "#${r}${g}${b}".uppercase()
}

@Composable
fun StudioDetailScreen(
    preset: PixelPreset,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onBack: () -> Unit
) {
    var selectedGridSize by remember { mutableStateOf<PixelGridSize>(PixelGridSize.Grid5x5) }

    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline
    val defaultCanvasBg = MaterialTheme.colorScheme.background

    var customActiveColor by remember { mutableStateOf<Color?>(null) }
    var selectedActiveColor by remember { mutableStateOf<Color>(primaryColor) }

    var customInactiveColor by remember { mutableStateOf<Color?>(null) }
    var selectedInactiveColor by remember { mutableStateOf<Color>(outlineColor) }

    var customCanvasBgColor by remember { mutableStateOf<Color?>(null) }
    var selectedCanvasBgColor by remember { mutableStateOf<Color>(defaultCanvasBg) }

    var loaderSize by remember { mutableStateOf(48.dp) }
    var speedMultiplier by remember { mutableStateOf(1.0f) }
    var isPlaying by remember { mutableStateOf(true) }
    var exportFormat by remember { mutableStateOf(ExportFormat.KOTLIN_CODE) }

    var activeColorTarget by remember { mutableStateOf<ColorPickerTarget?>(null) }

    @Suppress("DEPRECATION")
    val clipboardManager = LocalClipboardManager.current
    var isCopied by remember { mutableStateOf(false) }

    // Color Picker Dialog Modal
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
        // Header Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(1.dp, outlineColor, RoundedCornerShape(8.dp))
                        .clickable(onClick = onBack)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Gallery",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "${preset.name} Loader",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
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

        // Adaptive Responsive Layout
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            val isCompact = maxWidth < 600.dp

            val currentAnimation = preset.selectAnimation(selectedGridSize)
            val generatedCode = CodeExporter.generateCode(preset, selectedGridSize, loaderSize.value.toInt(), speedMultiplier)
            val generatedSvg = SvgExporter.exportAnimatedSvg(
                animation = currentAnimation,
                activeHex = selectedActiveColor.toHexString(),
                inactiveHex = selectedInactiveColor.toHexString(),
                speedMultiplier = speedMultiplier
            )
            val activeExportText = if (exportFormat == ExportFormat.KOTLIN_CODE) generatedCode else generatedSvg

            if (isCompact) {
                // Mobile Portrait Layout: Scrollable Vertical Column
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Stage Canvas Card
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GridSizeOptionButton(label = "5×5", isSelected = selectedGridSize == PixelGridSize.Grid5x5) { selectedGridSize = PixelGridSize.Grid5x5 }
                            GridSizeOptionButton(label = "7×7", isSelected = selectedGridSize == PixelGridSize.Grid7x7) { selectedGridSize = PixelGridSize.Grid7x7 }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(selectedCanvasBgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            PixelLoader(
                                preset = preset,
                                gridSize = selectedGridSize,
                                modifier = Modifier.size(loaderSize),
                                color = selectedActiveColor,
                                inactiveColor = selectedInactiveColor,
                                speedMultiplier = if (isPlaying) speedMultiplier else 0.001f
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                                .clickable { isPlaying = !isPlaying }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isPlaying) "Pause" else "Play",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    // Configuration & Code Exporter Card
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Configuration", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        // ON Color
                        Column {
                            Text("ON Color", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(primaryColor, Color(0xFF6366F1), Color(0xFF10B981)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = selectedActiveColor == c) { selectedActiveColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = customActiveColor,
                                    isSelected = selectedActiveColor == customActiveColor && customActiveColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = { if (customActiveColor != null) selectedActiveColor = customActiveColor!! else activeColorTarget = ColorPickerTarget.ON_COLOR },
                                    onEdit = { activeColorTarget = ColorPickerTarget.ON_COLOR }
                                )
                            }
                        }

                        // OFF Color
                        Column {
                            Text("OFF Color", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(outlineColor, Color(0xFF18181B), Color(0xFFE4E4E7)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = selectedInactiveColor == c) { selectedInactiveColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = customInactiveColor,
                                    isSelected = selectedInactiveColor == customInactiveColor && customInactiveColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = { if (customInactiveColor != null) selectedInactiveColor = customInactiveColor!! else activeColorTarget = ColorPickerTarget.OFF_COLOR },
                                    onEdit = { activeColorTarget = ColorPickerTarget.OFF_COLOR }
                                )
                            }
                        }

                        // Canvas BG
                        Column {
                            Text("Canvas BG", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(defaultCanvasBg, Color(0xFF000000), Color(0xFF0F172A)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = selectedCanvasBgColor == c) { selectedCanvasBgColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = customCanvasBgColor,
                                    isSelected = selectedCanvasBgColor == customCanvasBgColor && customCanvasBgColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = { if (customCanvasBgColor != null) selectedCanvasBgColor = customCanvasBgColor!! else activeColorTarget = ColorPickerTarget.CANVAS_BG },
                                    onEdit = { activeColorTarget = ColorPickerTarget.CANVAS_BG }
                                )
                            }
                        }

                        // Size
                        Column {
                            Text("Size", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(24.dp, 36.dp, 48.dp, 64.dp).forEach { s ->
                                    OptionBadge(label = "${s.value.toInt()}dp", isSelected = loaderSize == s) { loaderSize = s }
                                }
                            }
                        }

                        // Speed
                        Column {
                            Text("Speed Multiplier", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { spd ->
                                    OptionBadge(label = "${spd}x", isSelected = speedMultiplier == spd) { speedMultiplier = spd }
                                }
                            }
                        }

                        // Export Format & Code Section
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OptionBadge(
                                        label = "Kotlin Code",
                                        isSelected = exportFormat == ExportFormat.KOTLIN_CODE
                                    ) {
                                        exportFormat = ExportFormat.KOTLIN_CODE
                                        isCopied = false
                                    }
                                    OptionBadge(
                                        label = "SVG Vector",
                                        isSelected = exportFormat == ExportFormat.SVG_VECTOR
                                    ) {
                                        exportFormat = ExportFormat.SVG_VECTOR
                                        isCopied = false
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isCopied) Color(0xFF10B981) else MaterialTheme.colorScheme.surfaceVariant)
                                        .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                        .clickable {
                                            clipboardManager.setText(AnnotatedString(activeExportText))
                                            isCopied = true
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isCopied) "Copied!" else if (exportFormat == ExportFormat.KOTLIN_CODE) "Copy Code" else "Copy SVG",
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .border(1.dp, outlineColor, RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState())
                                ) {
                                    Text(
                                        text = activeExportText,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                // Wide Desktop / Tablet Layout: 2 Columns Side-by-Side
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1.2f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GridSizeOptionButton(label = "5×5", isSelected = selectedGridSize == PixelGridSize.Grid5x5) { selectedGridSize = PixelGridSize.Grid5x5 }
                            GridSizeOptionButton(label = "7×7", isSelected = selectedGridSize == PixelGridSize.Grid7x7) { selectedGridSize = PixelGridSize.Grid7x7 }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(selectedCanvasBgColor),
                            contentAlignment = Alignment.Center
                        ) {
                            PixelLoader(
                                preset = preset,
                                gridSize = selectedGridSize,
                                modifier = Modifier.size(loaderSize),
                                color = selectedActiveColor,
                                inactiveColor = selectedInactiveColor,
                                speedMultiplier = if (isPlaying) speedMultiplier else 0.001f
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                                .clickable { isPlaying = !isPlaying }
                                .padding(horizontal = 20.dp, vertical = 10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Play/Pause",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (isPlaying) "Pause" else "Play",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text("Configuration", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                        // ON Color
                        Column {
                            Text("ON Color", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(primaryColor, Color(0xFF6366F1), Color(0xFF10B981)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = selectedActiveColor == c) { selectedActiveColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = customActiveColor,
                                    isSelected = selectedActiveColor == customActiveColor && customActiveColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = { if (customActiveColor != null) selectedActiveColor = customActiveColor!! else activeColorTarget = ColorPickerTarget.ON_COLOR },
                                    onEdit = { activeColorTarget = ColorPickerTarget.ON_COLOR }
                                )
                            }
                        }

                        // OFF Color
                        Column {
                            Text("OFF Color", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(outlineColor, Color(0xFF18181B), Color(0xFFE4E4E7)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = selectedInactiveColor == c) { selectedInactiveColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = customInactiveColor,
                                    isSelected = selectedInactiveColor == customInactiveColor && customInactiveColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = { if (customInactiveColor != null) selectedInactiveColor = customInactiveColor!! else activeColorTarget = ColorPickerTarget.OFF_COLOR },
                                    onEdit = { activeColorTarget = ColorPickerTarget.OFF_COLOR }
                                )
                            }
                        }

                        // Canvas BG
                        Column {
                            Text("Canvas BG", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(defaultCanvasBg, Color(0xFF000000), Color(0xFF0F172A)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = selectedCanvasBgColor == c) { selectedCanvasBgColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = customCanvasBgColor,
                                    isSelected = selectedCanvasBgColor == customCanvasBgColor && customCanvasBgColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = { if (customCanvasBgColor != null) selectedCanvasBgColor = customCanvasBgColor!! else activeColorTarget = ColorPickerTarget.CANVAS_BG },
                                    onEdit = { activeColorTarget = ColorPickerTarget.CANVAS_BG }
                                )
                            }
                        }

                        // Size
                        Column {
                            Text("Size", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(24.dp, 36.dp, 48.dp, 64.dp).forEach { s ->
                                    OptionBadge(label = "${s.value.toInt()}dp", isSelected = loaderSize == s) { loaderSize = s }
                                }
                            }
                        }

                        // Speed
                        Column {
                            Text("Speed Multiplier", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { spd ->
                                    OptionBadge(label = "${spd}x", isSelected = speedMultiplier == spd) { speedMultiplier = spd }
                                }
                            }
                        }

                        // Export Format & Code Section
                        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    OptionBadge(
                                        label = "Kotlin Code",
                                        isSelected = exportFormat == ExportFormat.KOTLIN_CODE
                                    ) {
                                        exportFormat = ExportFormat.KOTLIN_CODE
                                        isCopied = false
                                    }
                                    OptionBadge(
                                        label = "SVG Vector",
                                        isSelected = exportFormat == ExportFormat.SVG_VECTOR
                                    ) {
                                        exportFormat = ExportFormat.SVG_VECTOR
                                        isCopied = false
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isCopied) Color(0xFF10B981) else MaterialTheme.colorScheme.surfaceVariant)
                                        .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                        .clickable {
                                            clipboardManager.setText(AnnotatedString(activeExportText))
                                            isCopied = true
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = if (isCopied) "Copied!" else if (exportFormat == ExportFormat.KOTLIN_CODE) "Copy Code" else "Copy SVG",
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .border(1.dp, outlineColor, RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState())
                                ) {
                                    Text(
                                        text = activeExportText,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontSize = 11.sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
