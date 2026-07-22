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
import androidx.compose.foundation.text.selection.SelectionContainer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meet.compose.loaders.LibraryConfig
import com.meet.compose.loaders.PixelLoader
import com.meet.compose.loaders.export.CodeExporter
import com.meet.compose.loaders.export.SvgExporter
import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.model.PixelPreset
import com.meet.compose.loaders.model.PixelShape
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
    onBack: () -> Unit,
    viewModel: StudioDetailViewModel = viewModel { StudioDetailViewModel() }
) {
    val outlineColor = MaterialTheme.colorScheme.outline
    val defaultCanvasBg = MaterialTheme.colorScheme.background
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(outlineColor, defaultCanvasBg) {
        viewModel.onIntent(StudioDetailIntent.InitializeDefaultColors(outlineColor, defaultCanvasBg))
    }

    @Suppress("DEPRECATION")
    val clipboardManager = LocalClipboardManager.current

    val dependencySetupText = """
        // Add the dependency to your commonMain dependencies in build.gradle.kts:
        commonMain.dependencies {
            implementation("${LibraryConfig.DEPENDENCY_COORDINATE}")
        }

        // Or reference local project module inside your multiplatform workspace:
        commonMain.dependencies {
            implementation(project(":loaders"))
        }
    """.trimIndent()

    // Color Picker Dialog Modal
    uiState.activeColorTarget?.let { target ->
        val initial = when (target) {
            ColorPickerTarget.ON_COLOR -> uiState.customActiveColor ?: Color(0xFFEC4899)
            ColorPickerTarget.OFF_COLOR -> uiState.customInactiveColor ?: Color(0xFF27272A)
            ColorPickerTarget.CANVAS_BG -> uiState.customCanvasBgColor ?: Color(0xFF0F172A)
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
                        viewModel.onIntent(StudioDetailIntent.UpdateCustomActiveColor(selected))
                    }
                    ColorPickerTarget.OFF_COLOR -> {
                        viewModel.onIntent(StudioDetailIntent.UpdateCustomInactiveColor(selected))
                    }
                    ColorPickerTarget.CANVAS_BG -> {
                        viewModel.onIntent(StudioDetailIntent.UpdateCustomCanvasBgColor(selected))
                    }
                }
            },
            onDismissRequest = { viewModel.onIntent(StudioDetailIntent.CloseColorPicker) }
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

            val currentAnimation = preset.selectAnimation(uiState.selectedGridSize)
            val generatedCode = CodeExporter.generateCode(preset, uiState.selectedGridSize, uiState.loaderSize.value.toInt(), uiState.speedMultiplier, uiState.selectedPixelShape)
            val generatedSvg = SvgExporter.exportAnimatedSvg(
                animation = currentAnimation,
                activeHex = uiState.selectedActiveColor.toHexString(),
                inactiveHex = uiState.selectedInactiveColor.toHexString(),
                speedMultiplier = uiState.speedMultiplier,
                shape = uiState.selectedPixelShape
            )
            val activeExportText = if (uiState.exportFormat == ExportFormat.KOTLIN_CODE) generatedCode else generatedSvg

            // -------------------------------------------------------------
            // REUSABLE SECTIONS (MovableContent)
            // -------------------------------------------------------------

            val gridSizeSelector = remember(uiState.selectedGridSize) {
                movableContentOf {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GridSizeOptionButton(label = "5×5", isSelected = uiState.selectedGridSize == PixelGridSize.Grid5x5) { viewModel.onIntent(StudioDetailIntent.SelectGridSize(PixelGridSize.Grid5x5)) }
                        GridSizeOptionButton(label = "7×7", isSelected = uiState.selectedGridSize == PixelGridSize.Grid7x7) { viewModel.onIntent(StudioDetailIntent.SelectGridSize(PixelGridSize.Grid7x7)) }
                    }
                }
            }

            val previewCanvas = remember(preset, uiState.selectedGridSize, uiState.loaderSize, uiState.selectedActiveColor, uiState.selectedInactiveColor, uiState.selectedPixelShape, uiState.isPlaying, uiState.speedMultiplier, uiState.selectedCanvasBgColor) {
                movableContentOf { isComp: Boolean ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (isComp) 220.dp else 280.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(uiState.selectedCanvasBgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        PixelLoader(
                            preset = preset,
                            gridSize = uiState.selectedGridSize,
                            modifier = Modifier.size(uiState.loaderSize),
                            color = uiState.selectedActiveColor,
                            inactiveColor = uiState.selectedInactiveColor,
                            shape = uiState.selectedPixelShape,
                            speedMultiplier = if (uiState.isPlaying) uiState.speedMultiplier else 0.001f
                        )
                    }
                }
            }

            val playPauseControls = remember(uiState.isPlaying) {
                movableContentOf {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                            .clickable { viewModel.onIntent(StudioDetailIntent.UpdateIsPlaying(!uiState.isPlaying)) }
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (uiState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (uiState.isPlaying) "Pause" else "Play Animation",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            val colorControls = remember(uiState.selectedActiveColor, uiState.customActiveColor, uiState.selectedInactiveColor, uiState.customInactiveColor, uiState.selectedCanvasBgColor, uiState.customCanvasBgColor, outlineColor, defaultCanvasBg) {
                movableContentOf {
                    val activeVal = uiState.customActiveColor
                    val inactiveVal = uiState.customInactiveColor
                    val canvasBgVal = uiState.customCanvasBgColor

                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // ON Color
                        Column {
                            Text("Active Color (ON)", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(Color(0xFF6366F1), Color(0xFF10B981), Color(0xFFF59E0B)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = uiState.selectedActiveColor == c) { viewModel.onIntent(StudioDetailIntent.SelectActiveColor(c)) }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = activeVal,
                                    isSelected = uiState.selectedActiveColor == activeVal,
                                    outlineColor = outlineColor,
                                    onSelect = {
                                        if (activeVal != null) {
                                            viewModel.onIntent(StudioDetailIntent.SelectActiveColor(activeVal))
                                        } else {
                                            viewModel.onIntent(StudioDetailIntent.OpenColorPicker(ColorPickerTarget.ON_COLOR))
                                        }
                                    },
                                    onEdit = { viewModel.onIntent(StudioDetailIntent.OpenColorPicker(ColorPickerTarget.ON_COLOR)) }
                                )
                            }
                        }

                        // OFF Color
                        Column {
                            Text("Inactive Color (OFF)", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(outlineColor, Color(0xFF18181B), Color(0xFFE4E4E7)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = uiState.selectedInactiveColor == c) { viewModel.onIntent(StudioDetailIntent.SelectInactiveColor(c)) }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = inactiveVal,
                                    isSelected = uiState.selectedInactiveColor == inactiveVal,
                                    outlineColor = outlineColor,
                                    onSelect = {
                                        if (inactiveVal != null) {
                                            viewModel.onIntent(StudioDetailIntent.SelectInactiveColor(inactiveVal))
                                        } else {
                                            viewModel.onIntent(StudioDetailIntent.OpenColorPicker(ColorPickerTarget.OFF_COLOR))
                                        }
                                    },
                                    onEdit = { viewModel.onIntent(StudioDetailIntent.OpenColorPicker(ColorPickerTarget.OFF_COLOR)) }
                                )
                            }
                        }

                        // Canvas BG
                        Column {
                            Text("Canvas BG", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.CenterVertically) {
                                listOf(defaultCanvasBg, Color(0xFF000000), Color(0xFF0F172A)).forEach { c ->
                                    ColorSwatch(color = c, isSelected = uiState.selectedCanvasBgColor == c) { viewModel.onIntent(StudioDetailIntent.SelectCanvasBgColor(c)) }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = canvasBgVal,
                                    isSelected = uiState.selectedCanvasBgColor == canvasBgVal,
                                    outlineColor = outlineColor,
                                    onSelect = {
                                        if (canvasBgVal != null) {
                                            viewModel.onIntent(StudioDetailIntent.SelectCanvasBgColor(canvasBgVal))
                                        } else {
                                            viewModel.onIntent(StudioDetailIntent.OpenColorPicker(ColorPickerTarget.CANVAS_BG))
                                        }
                                    },
                                    onEdit = { viewModel.onIntent(StudioDetailIntent.OpenColorPicker(ColorPickerTarget.CANVAS_BG)) }
                                )
                            }
                        }
                    }
                }
            }

            val configControls = remember(uiState.loaderSize, uiState.speedMultiplier, uiState.selectedPixelShape) {
                movableContentOf {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Size
                        Column {
                            Text("Size", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(24.dp, 36.dp, 48.dp, 64.dp).forEach { s ->
                                    OptionBadge(label = "${s.value.toInt()}dp", isSelected = uiState.loaderSize == s) { viewModel.onIntent(StudioDetailIntent.UpdateLoaderSize(s)) }
                                }
                            }
                        }

                        // Speed
                        Column {
                            Text("Speed Multiplier", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { spd ->
                                    OptionBadge(label = "${spd}x", isSelected = uiState.speedMultiplier == spd) { viewModel.onIntent(StudioDetailIntent.UpdateSpeedMultiplier(spd)) }
                                }
                            }
                        }

                        // Shape
                        Column {
                            Text("Pixel Shape", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                OptionBadge(label = "Circle", isSelected = uiState.selectedPixelShape == PixelShape.Circle) { viewModel.onIntent(StudioDetailIntent.UpdatePixelShape(PixelShape.Circle)) }
                                OptionBadge(label = "Square", isSelected = uiState.selectedPixelShape == PixelShape.Square) { viewModel.onIntent(StudioDetailIntent.UpdatePixelShape(PixelShape.Square)) }
                                OptionBadge(label = "Rounded", isSelected = uiState.selectedPixelShape == PixelShape.RoundedSquare) { viewModel.onIntent(StudioDetailIntent.UpdatePixelShape(PixelShape.RoundedSquare)) }
                            }
                        }
                    }
                }
            }

            val exportSection = remember(uiState.exportFormat, uiState.isCopied, uiState.isDependencyCopied, dependencySetupText) {
                movableContentOf { activeText: String ->
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Dependency Setup Banner
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                                .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Dependency Setup",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (uiState.isDependencyCopied) Color(0xFF10B981) else MaterialTheme.colorScheme.surfaceVariant)
                                        .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                        .clickable {
                                            clipboardManager.setText(AnnotatedString(dependencySetupText))
                                            viewModel.onIntent(StudioDetailIntent.SetDependencyCopied(true))
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (uiState.isDependencyCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                            contentDescription = "Copy Setup",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (uiState.isDependencyCopied) "Copied!" else "Copy Setup",
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                    .padding(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState())
                                        .horizontalScroll(rememberScrollState())
                                ) {
                                    SelectionContainer {
                                        Text(
                                            text = dependencySetupText,
                                            fontFamily = FontFamily.Monospace,
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        // Export Options Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OptionBadge(
                                    label = "Kotlin Code",
                                    isSelected = uiState.exportFormat == ExportFormat.KOTLIN_CODE
                                ) {
                                    viewModel.onIntent(StudioDetailIntent.UpdateExportFormat(ExportFormat.KOTLIN_CODE))
                                }
                                OptionBadge(
                                    label = "SVG Vector",
                                    isSelected = uiState.exportFormat == ExportFormat.SVG_VECTOR
                                ) {
                                    viewModel.onIntent(StudioDetailIntent.UpdateExportFormat(ExportFormat.SVG_VECTOR))
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (uiState.isCopied) Color(0xFF10B981) else MaterialTheme.colorScheme.surfaceVariant)
                                    .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                    .clickable {
                                        clipboardManager.setText(AnnotatedString(activeText))
                                        viewModel.onIntent(StudioDetailIntent.SetCopied(true))
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (uiState.isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (uiState.isCopied) "Copied!" else if (uiState.exportFormat == ExportFormat.KOTLIN_CODE) "Copy Code" else "Copy SVG",
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
                                SelectionContainer {
                                    Text(
                                        text = activeText,
                                        fontFamily = FontFamily.Monospace,
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // -------------------------------------------------------------
            // LAYOUT RENDERING
            // -------------------------------------------------------------

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
                        gridSizeSelector()
                        previewCanvas(true)
                        playPauseControls()
                    }

                    // Configuration Panel
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        colorControls()
                        configControls()
                    }

                    // Code / SVG Panel
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        exportSection(activeExportText)
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
                        gridSizeSelector()
                        previewCanvas(false)
                        playPauseControls()
                    }

                    Column(
                        modifier = Modifier
                            .weight(1.5f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, outlineColor, RoundedCornerShape(16.dp))
                            .verticalScroll(rememberScrollState())
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        colorControls()
                        configControls()
                        exportSection(activeExportText)
                    }
                }
            }
        }
    }
}
