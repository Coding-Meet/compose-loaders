package com.meet.compose.loaders.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlin.math.abs

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorPickerDialog(
    initialColor: Color,
    title: String = "Pick Color",
    onColorSelected: (Color) -> Unit,
    onDismissRequest: () -> Unit
) {
    var hue by remember { mutableStateOf(colorToHue(initialColor)) }
    var saturation by remember { mutableStateOf(colorToSaturation(initialColor)) }
    var value by remember { mutableStateOf(colorToValue(initialColor)) }

    val currentColor = remember(hue, saturation, value) {
        hsvToColor(hue, saturation, value)
    }

    var hexInput by remember { mutableStateOf(colorToHex(currentColor)) }

    // Sync hexInput when currentColor changes from canvas/slider
    remember(currentColor) {
        hexInput = colorToHex(currentColor)
    }

    val presetPalette = remember {
        listOf(
            Color(0xFFFFFFFF), Color(0xFFF87171), Color(0xFFFB923C), Color(0xFFFBBF24),
            Color(0xFFA3E635), Color(0xFF34D399), Color(0xFF2DD4BF), Color(0xFF38BDF8),
            Color(0xFF60A5FA), Color(0xFF818CF8), Color(0xFFA78BFA), Color(0xFFC084FC),
            Color(0xFFE879F9), Color(0xFFF43F5E), Color(0xFF64748B), Color(0xFF18181B)
        )
    }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
            modifier = Modifier.fillMaxWidth(0.95f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Palette",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(onClick = onDismissRequest) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 2D Saturation / Value Spectrum Canvas
                SatValCanvas(
                    hue = hue,
                    saturation = saturation,
                    value = value,
                    onSatValChanged = { newSat, newVal ->
                        saturation = newSat
                        value = newVal
                    }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Hue Rainbow Slider
                Text(
                    text = "Hue Spectrum",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                HueSlider(
                    hue = hue,
                    onHueChanged = { newHue -> hue = newHue }
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Preview Box & Hex Input Field
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(currentColor)
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
                    )

                    OutlinedTextField(
                        value = hexInput,
                        onValueChange = { input ->
                            hexInput = input
                            val parsed = parseHexColor(input)
                            if (parsed != null) {
                                hue = colorToHue(parsed)
                                saturation = colorToSaturation(parsed)
                                value = colorToValue(parsed)
                            }
                        },
                        label = { Text("HEX Code (#RRGGBB)", fontSize = 11.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Quick Presets",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Quick Palette Swatches
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    presetPalette.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (currentColor == color) 2.dp else 1.dp,
                                    color = if (currentColor == color) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                                    shape = CircleShape
                                )
                                .clickable {
                                    hue = colorToHue(color)
                                    saturation = colorToSaturation(color)
                                    value = colorToValue(color)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (currentColor == color) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = if (color == Color.White) Color.Black else Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            onColorSelected(currentColor)
                            onDismissRequest()
                        }
                    ) {
                        Text("Apply", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SatValCanvas(
    hue: Float,
    saturation: Float,
    value: Float,
    onSatValChanged: (Float, Float) -> Unit
) {
    val pureHueColor = remember(hue) { hsvToColor(hue, 1f, 1f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .pointerInput(hue) {
                detectTapGestures { offset ->
                    val sat = (offset.x / size.width).coerceIn(0f, 1f)
                    val valVal = (1f - (offset.y / size.height)).coerceIn(0f, 1f)
                    onSatValChanged(sat, valVal)
                }
            }
            .pointerInput(hue) {
                detectDragGestures { change, _ ->
                    val sat = (change.position.x / size.width).coerceIn(0f, 1f)
                    val valVal = (1f - (change.position.y / size.height)).coerceIn(0f, 1f)
                    onSatValChanged(sat, valVal)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(150.dp)) {
            // Horizontal Saturation Gradient (White to Pure Hue)
            drawRect(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color.White, pureHueColor)
                )
            )
            // Vertical Value Gradient (Transparent to Black)
            drawRect(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black)
                )
            )

            // Selector Circle Indicator
            val selectorX = saturation * size.width
            val selectorY = (1f - value) * size.height
            drawCircle(
                color = Color.White,
                radius = 8.dp.toPx(),
                center = Offset(selectorX, selectorY),
                style = Stroke(width = 3.dp.toPx())
            )
            drawCircle(
                color = Color.Black,
                radius = 9.5.dp.toPx(),
                center = Offset(selectorX, selectorY),
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }
}

@Composable
private fun HueSlider(
    hue: Float,
    onHueChanged: (Float) -> Unit
) {
    val rainbowColors = remember {
        listOf(
            Color.Red, Color.Yellow, Color.Green,
            Color.Cyan, Color.Blue, Color.Magenta, Color.Red
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp))
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val newHue = (offset.x / size.width).coerceIn(0f, 1f) * 360f
                    onHueChanged(newHue)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val newHue = (change.position.x / size.width).coerceIn(0f, 1f) * 360f
                    onHueChanged(newHue)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().height(24.dp)) {
            drawRect(
                brush = Brush.horizontalGradient(colors = rainbowColors)
            )

            // Hue Handle Indicator
            val handleX = (hue / 360f).coerceIn(0f, 1f) * size.width
            drawCircle(
                color = Color.White,
                radius = 10.dp.toPx(),
                center = Offset(handleX, size.height / 2f),
                style = Stroke(width = 3.dp.toPx())
            )
            drawCircle(
                color = Color.Black,
                radius = 11.5.dp.toPx(),
                center = Offset(handleX, size.height / 2f),
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }
}

// HSV Utilities
fun hsvToColor(hue: Float, saturation: Float, value: Float): Color {
    val c = value * saturation
    val x = c * (1 - abs((hue / 60f) % 2 - 1))
    val m = value - c

    val (r1, g1, b1) = when {
        hue < 60f -> Triple(c, x, 0f)
        hue < 120f -> Triple(x, c, 0f)
        hue < 180f -> Triple(0f, c, x)
        hue < 240f -> Triple(0f, x, c)
        hue < 300f -> Triple(x, 0f, c)
        else -> Triple(c, 0f, x)
    }

    return Color(
        red = r1 + m,
        green = g1 + m,
        blue = b1 + m,
        alpha = 1f
    )
}

fun colorToHue(color: Color): Float {
    val r = color.red
    val g = color.green
    val b = color.blue
    val max = maxOf(r, g, b)
    val min = minOf(r, g, b)
    val delta = max - min
    if (delta == 0f) return 0f

    val hue = when (max) {
        r -> (g - b) / delta % 6f
        g -> (b - r) / delta + 2f
        else -> (r - g) / delta + 4f
    } * 60f

    return if (hue < 0f) hue + 360f else hue
}

fun colorToSaturation(color: Color): Float {
    val max = maxOf(color.red, color.green, color.blue)
    val min = minOf(color.red, color.green, color.blue)
    return if (max == 0f) 0f else (max - min) / max
}

fun colorToValue(color: Color): Float {
    return maxOf(color.red, color.green, color.blue)
}

private fun colorToHex(color: Color): String {
    val r = (color.red * 255).toInt().coerceIn(0, 255)
    val g = (color.green * 255).toInt().coerceIn(0, 255)
    val b = (color.blue * 255).toInt().coerceIn(0, 255)
    return "#%02X%02X%02X".format(r, g, b)
}

private fun parseHexColor(hex: String): Color? {
    val clean = hex.removePrefix("#").trim()
    if (clean.length != 6) return null
    return try {
        val colorInt = clean.toLong(16)
        Color(
            red = ((colorInt shr 16) and 0xFF) / 255f,
            green = ((colorInt shr 8) and 0xFF) / 255f,
            blue = (colorInt and 0xFF) / 255f,
            alpha = 1.0f
        )
    } catch (_: Exception) {
        null
    }
}
