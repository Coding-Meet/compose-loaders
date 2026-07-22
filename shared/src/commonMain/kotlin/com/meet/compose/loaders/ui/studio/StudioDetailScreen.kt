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
) {
    val viewModel = viewModel<StudioDetailViewModel> { StudioDetailViewModel() }
    val outlineColor = MaterialTheme.colorScheme.outline
    val defaultCanvasBg = MaterialTheme.colorScheme.background

    androidx.compose.runtime.LaunchedEffect(outlineColor, defaultCanvasBg) {
        if (viewModel.selectedInactiveColor == Color.Unspecified) {
            viewModel.selectedInactiveColor = outlineColor
        }
        if (viewModel.selectedCanvasBgColor == Color.Unspecified) {
            viewModel.selectedCanvasBgColor = defaultCanvasBg
        }
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
    viewModel.activeColorTarget?.let { target ->
        val initial = when (target) {
            ColorPickerTarget.ON_COLOR -> viewModel.customActiveColor ?: Color(0xFFEC4899)
            ColorPickerTarget.OFF_COLOR -> viewModel.customInactiveColor ?: Color(0xFF27272A)
            ColorPickerTarget.CANVAS_BG -> viewModel.customCanvasBgColor ?: Color(0xFF0F172A)
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
                        viewModel.customActiveColor = selected
                        viewModel.selectedActiveColor = selected
                    }

                    ColorPickerTarget.OFF_COLOR -> {
                        viewModel.customInactiveColor = selected
                        viewModel.selectedInactiveColor = selected
                    }

                    ColorPickerTarget.CANVAS_BG -> {
                        viewModel.customCanvasBgColor = selected
                        viewModel.selectedCanvasBgColor = selected
                    }
                }
            },
            onDismissRequest = { viewModel.activeColorTarget = null }
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

            val currentAnimation = preset.selectAnimation(viewModel.selectedGridSize)
            val generatedCode = CodeExporter.generateCode(
                preset,
                viewModel.selectedGridSize,
                viewModel.loaderSize.value.toInt(),
                viewModel.speedMultiplier,
                viewModel.selectedPixelShape
            )
            val generatedSvg = SvgExporter.exportAnimatedSvg(
                animation = currentAnimation,
                activeHex = viewModel.selectedActiveColor.toHexString(),
                inactiveHex = viewModel.selectedInactiveColor.toHexString(),
                speedMultiplier = viewModel.speedMultiplier,
                shape = viewModel.selectedPixelShape
            )
            val activeExportText =
                if (viewModel.exportFormat == ExportFormat.KOTLIN_CODE) generatedCode else generatedSvg

            // -------------------------------------------------------------
            // REUSABLE SECTIONS (MovableContent)
            // -------------------------------------------------------------

            val gridSizeSelector = remember(viewModel.selectedGridSize) {
                movableContentOf {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        GridSizeOptionButton(
                            label = "5×5",
                            isSelected = viewModel.selectedGridSize == PixelGridSize.Grid5x5
                        ) { viewModel.selectedGridSize = PixelGridSize.Grid5x5 }
                        GridSizeOptionButton(
                            label = "7×7",
                            isSelected = viewModel.selectedGridSize == PixelGridSize.Grid7x7
                        ) { viewModel.selectedGridSize = PixelGridSize.Grid7x7 }
                    }
                }
            }

            val previewCanvas = remember(
                preset,
                viewModel.selectedGridSize,
                viewModel.loaderSize,
                viewModel.selectedActiveColor,
                viewModel.selectedInactiveColor,
                viewModel.selectedPixelShape,
                viewModel.isPlaying,
                viewModel.speedMultiplier,
                viewModel.selectedCanvasBgColor
            ) {
                movableContentOf { isComp: Boolean ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(if (isComp) 220.dp else 280.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(viewModel.selectedCanvasBgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        PixelLoader(
                            preset = preset,
                            gridSize = viewModel.selectedGridSize,
                            modifier = Modifier.size(viewModel.loaderSize),
                            color = viewModel.selectedActiveColor,
                            inactiveColor = viewModel.selectedInactiveColor,
                            shape = viewModel.selectedPixelShape,
                            speedMultiplier = if (viewModel.isPlaying) viewModel.speedMultiplier else 0.001f
                        )
                    }
                }
            }

            val playPauseControls = remember(viewModel.isPlaying) {
                movableContentOf {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .border(1.dp, outlineColor, RoundedCornerShape(10.dp))
                            .clickable { viewModel.isPlaying = !viewModel.isPlaying }
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (viewModel.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Play/Pause",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (viewModel.isPlaying) "Pause" else "Play Animation",
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            val colorControls = remember(
                viewModel.selectedActiveColor,
                viewModel.customActiveColor,
                viewModel.selectedInactiveColor,
                viewModel.customInactiveColor,
                viewModel.selectedCanvasBgColor,
                viewModel.customCanvasBgColor,
                outlineColor,
                defaultCanvasBg
            ) {
                movableContentOf {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // ON Color
                        Column {
                            Text(
                                "Active Color (ON)",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf(
                                    Color(0xFF6366F1),
                                    Color(0xFF10B981),
                                    Color(0xFFF59E0B)
                                ).forEach { c ->
                                    ColorSwatch(
                                        color = c,
                                        isSelected = viewModel.selectedActiveColor == c
                                    ) { viewModel.selectedActiveColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = viewModel.customActiveColor,
                                    isSelected = viewModel.selectedActiveColor == viewModel.customActiveColor && viewModel.customActiveColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = {
                                        if (viewModel.customActiveColor != null) viewModel.selectedActiveColor =
                                            viewModel.customActiveColor!! else viewModel.activeColorTarget =
                                            ColorPickerTarget.ON_COLOR
                                    },
                                    onEdit = {
                                        viewModel.activeColorTarget = ColorPickerTarget.ON_COLOR
                                    }
                                )
                            }
                        }

                        // OFF Color
                        Column {
                            Text(
                                "Inactive Color (OFF)",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf(
                                    outlineColor,
                                    Color(0xFF18181B),
                                    Color(0xFFE4E4E7)
                                ).forEach { c ->
                                    ColorSwatch(
                                        color = c,
                                        isSelected = viewModel.selectedInactiveColor == c
                                    ) { viewModel.selectedInactiveColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = viewModel.customInactiveColor,
                                    isSelected = viewModel.selectedInactiveColor == viewModel.customInactiveColor && viewModel.customInactiveColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = {
                                        if (viewModel.customInactiveColor != null) viewModel.selectedInactiveColor =
                                            viewModel.customInactiveColor!! else viewModel.activeColorTarget =
                                            ColorPickerTarget.OFF_COLOR
                                    },
                                    onEdit = {
                                        viewModel.activeColorTarget = ColorPickerTarget.OFF_COLOR
                                    }
                                )
                            }
                        }

                        // Canvas BG
                        Column {
                            Text(
                                "Canvas BG",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                listOf(
                                    defaultCanvasBg,
                                    Color(0xFF000000),
                                    Color(0xFF0F172A)
                                ).forEach { c ->
                                    ColorSwatch(
                                        color = c,
                                        isSelected = viewModel.selectedCanvasBgColor == c
                                    ) { viewModel.selectedCanvasBgColor = c }
                                }
                                DynamicCustomColorSwatch(
                                    customColor = viewModel.customCanvasBgColor,
                                    isSelected = viewModel.selectedCanvasBgColor == viewModel.customCanvasBgColor && viewModel.customCanvasBgColor != null,
                                    outlineColor = outlineColor,
                                    onSelect = {
                                        if (viewModel.customCanvasBgColor != null) viewModel.selectedCanvasBgColor =
                                            viewModel.customCanvasBgColor!! else viewModel.activeColorTarget =
                                            ColorPickerTarget.CANVAS_BG
                                    },
                                    onEdit = {
                                        viewModel.activeColorTarget = ColorPickerTarget.CANVAS_BG
                                    }
                                )
                            }
                        }
                    }
                }
            }

            val configControls = remember(
                viewModel.loaderSize,
                viewModel.speedMultiplier,
                viewModel.selectedPixelShape
            ) {
                movableContentOf {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        // Size
                        Column {
                            Text(
                                "Size",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(24.dp, 36.dp, 48.dp, 64.dp).forEach { s ->
                                    OptionBadge(
                                        label = "${s.value.toInt()}dp",
                                        isSelected = viewModel.loaderSize == s
                                    ) { viewModel.loaderSize = s }
                                }
                            }
                        }

                        // Speed
                        Column {
                            Text(
                                "Speed Multiplier",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                listOf(0.5f, 1.0f, 1.5f, 2.0f).forEach { spd ->
                                    OptionBadge(
                                        label = "${spd}x",
                                        isSelected = viewModel.speedMultiplier == spd
                                    ) { viewModel.speedMultiplier = spd }
                                }
                            }
                        }

                        // Shape
                        Column {
                            Text(
                                "Pixel Shape",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                OptionBadge(
                                    label = "Circle",
                                    isSelected = viewModel.selectedPixelShape == PixelShape.Circle
                                ) { viewModel.selectedPixelShape = PixelShape.Circle }
                                OptionBadge(
                                    label = "Square",
                                    isSelected = viewModel.selectedPixelShape == PixelShape.Square
                                ) { viewModel.selectedPixelShape = PixelShape.Square }
                                OptionBadge(
                                    label = "Rounded",
                                    isSelected = viewModel.selectedPixelShape == PixelShape.RoundedSquare
                                ) { viewModel.selectedPixelShape = PixelShape.RoundedSquare }
                            }
                        }
                    }
                }
            }

            val exportSection = remember(
                viewModel.exportFormat,
                viewModel.isCopied,
                viewModel.isDependencyCopied,
                dependencySetupText
            ) {
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
                                        .background(
                                            if (viewModel.isDependencyCopied) Color(
                                                0xFF10B981
                                            ) else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                        .clickable {
                                            clipboardManager.setText(
                                                AnnotatedString(
                                                    dependencySetupText
                                                )
                                            )
                                            viewModel.isDependencyCopied = true
                                        }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = if (viewModel.isDependencyCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                            contentDescription = "Copy Setup",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(12.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = if (viewModel.isDependencyCopied) "Copied!" else "Copy Setup",
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
                                    isSelected = viewModel.exportFormat == ExportFormat.KOTLIN_CODE
                                ) {
                                    viewModel.exportFormat = ExportFormat.KOTLIN_CODE
                                    viewModel.isCopied = false
                                }
                                OptionBadge(
                                    label = "SVG Vector",
                                    isSelected = viewModel.exportFormat == ExportFormat.SVG_VECTOR
                                ) {
                                    viewModel.exportFormat = ExportFormat.SVG_VECTOR
                                    viewModel.isCopied = false
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (viewModel.isCopied) Color(0xFF10B981) else MaterialTheme.colorScheme.surfaceVariant)
                                    .border(1.dp, outlineColor, RoundedCornerShape(6.dp))
                                    .clickable {
                                        clipboardManager.setText(AnnotatedString(activeText))
                                        viewModel.isCopied = true
                                    }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (viewModel.isCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (viewModel.isCopied) "Copied!" else if (viewModel.exportFormat == ExportFormat.KOTLIN_CODE) "Copy Code" else "Copy SVG",
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
