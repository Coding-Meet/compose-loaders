# Compose Loaders 🎨✨

[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg?logo=kotlin)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-blue.svg?logo=jetbrains)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Compose Loaders** is a lightweight, customizable, open-source **Compose Multiplatform (KMP)** library featuring **31 handcrafted pixel loading animations** across Android, iOS, Desktop (JVM), and Web (Wasm/JS).

---

## 🚀 Key Features

- 💎 **31 Handcrafted Animations**: Pixel-perfect loading animations designed in 5×5 and 7×7 dot matrix grids.
- 🌐 **100% Compose Multiplatform**: Pure Kotlin implementation in `commonMain` for Android, iOS, Desktop, and Web.
- ⚡ **Hardware Accelerated Rendering**: High-performance canvas drawing using Compose `DrawScope`.
- 🎨 **Deep Customization**: Customize active pixel colors, inactive pixel colors, canvas background, speed multiplier (`0.5x` - `2.0x`), dot size, and pixel shapes (`Circle`, `Square`, `RoundedSquare`).
- 🛠️ **Built-in SVG Exporter**: Export any animation frame directly into raw vector SVG string format.
- 📱 **Adaptive & Edge-to-Edge**: Integrated with Material 3, `Scaffold` safe drawing insets, and Jetpack Navigation Compose Type-Safe Navigation.

---

## 📦 Targets

| Platform | Target | Support |
| :--- | :--- | :--- |
| **Android** | `androidMain` | Android 7.0+ (API 24+) |
| **iOS** | `iosArm64`, `iosSimulatorArm64` | iOS 14.0+ |
| **Desktop** | `jvm` | Windows, macOS, Linux |
| **Web** | `wasmJs`, `js` | Modern Browsers |

---

## 💻 Installation

Add the dependency to your multiplatform module's `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    // Core Compose Loaders Library
    implementation("com.meet.compose:loaders:1.0.0")
}
```

Or reference the local project module:

```kotlin
commonMain.dependencies {
    implementation(project(":loaders"))
}
```

---

## 🚀 Quick Start & Usage

### 1. Basic Usage

Render any built-in preset with a single line of code:

```kotlin
import androidx.compose.runtime.Composable
import com.meet.compose.loaders.PixelLoader
import com.meet.compose.loaders.presets.PixelPresets

@Composable
fun LoadingExample() {
    PixelLoader(
        preset = PixelPresets.Framer
    )
}
```

---

### 2. Customizing Colors, Size & Speed

Customize the active color, inactive color, dot size, and playback speed:

```kotlin
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meet.compose.loaders.PixelLoader
import com.meet.compose.loaders.model.PixelGridSize
import com.meet.compose.loaders.presets.PixelPresets

@Composable
fun CustomizedLoader() {
    PixelLoader(
        preset = PixelPresets.Rocket,
        gridSize = PixelGridSize.Grid7x7,
        modifier = Modifier.size(64.dp),
        color = Color(0xFF6366F1),          // ON Color (Indigo)
        inactiveColor = Color(0xFF27272A),  // OFF Color (Dark Gray)
        backgroundColor = Color(0xFF0F172A),// Canvas BG
        speedMultiplier = 1.5f              // 1.5x Speed
    )
}
```

---

### 3. Grid Sizes

Presets support both `Grid5x5` and `Grid7x7` matrix formats:

```kotlin
PixelLoader(
    preset = PixelPresets.Galaxy,
    gridSize = PixelGridSize.Grid5x5  // 5x5 Matrix
)

PixelLoader(
    preset = PixelPresets.Galaxy,
    gridSize = PixelGridSize.Grid7x7  // 7x7 Matrix
)
```

---

### 4. Custom Pixel Frame Animations

Define custom frame grids programmatically:

```kotlin
import com.meet.compose.loaders.PixelLoader
import com.meet.compose.loaders.model.PixelAnimation

val CustomAnimation = PixelAnimation.fromGrids(
    columns = 5,
    rows = 5,
    frameGrids = listOf(
        listOf(
            false, false, true,  false, false,
            false, true,  true,  true,  false,
            true,  true,  false, true,  true,
            false, true,  true,  true,  false,
            false, false, true,  false, false
        )
    ),
    frameDurationMillis = 150L
)

@Composable
fun CustomLoaderExample() {
    PixelLoader(animation = CustomAnimation)
}
```

---

### 5. Exporting Frames to SVG

Export animation frames directly to vector SVG strings:

```kotlin
import com.meet.compose.loaders.export.SvgExporter
import com.meet.compose.loaders.presets.PixelPresets

val svgString = SvgExporter.exportFrameToSvg(
    animation = PixelPresets.Framer.grid5x5,
    frameIndex = 0,
    activeHex = "#6366F1",
    inactiveHex = "#18181B",
    canvasSize = 200
)
```

---

## 🎨 Catalog of 31 Preset Loaders

| Category | Presets |
| :--- | :--- |
| **Basic UI** | `Framer`, `Gradient`, `Target`, `Unboxing`, `DotDotDot`, `TinySpinner`, `Loading`, `Importing`, `Searching`, `Busy`, `Saving`, `Initialising`, `ClassicLoading` |
| **Motion & Physics** | `Newton`, `Bounce`, `Syncing`, `Ripple`, `RollingDice`, `Tumbleweed`, `TheGreatWave` |
| **Tech & Digital** | `Rocket`, `Comet`, `Infinite`, `BarChart`, `Flash`, `LoadingGraph`, `LoadingGIFs` |
| **Retro & Gaming** | `TheClaw`, `Galaxy`, `Sleepy`, `Pacman` |

---

## 🏗️ Library Architecture

```
com.meet.compose.loaders
├── PixelLoader.kt                    [PUBLIC] Core Composable entry point
│
├── model/                            [PUBLIC] Immutable Data Contracts
│   ├── PixelAnimation.kt             [PUBLIC] Frame sequence & timing specifier
│   ├── PixelGrid.kt                  [PUBLIC] 2D matrix boolean array wrapper
│   ├── PixelGridSize.kt              [PUBLIC] Sealed interface: Grid5x5, Grid7x7, Custom
│   ├── PixelPreset.kt                [PUBLIC] Data class for presets
│   ├── PixelColorConfig.kt           [PUBLIC] Color palette specifier
│   └── PixelShape.kt                 [PUBLIC] Dot geometry enum
│
├── presets/                          [PUBLIC & INTERNAL] Presets Catalog Registry
│   ├── PixelPresets.kt               [PUBLIC] Entry point exposing `val all: List<PixelPreset>`
│   └── internal/                     [INTERNAL] Categorized lazy preset stores
│
├── engine/internal/                  [INTERNAL] Internal Engine Components
│   └── GridMath.kt                   [INTERNAL] Layout & coordinate math
│
├── render/internal/                  [INTERNAL] Hardware Graphics Pipeline
│   └── PixelCanvasRenderer.kt        [INTERNAL] Compose DrawScope canvas renderer
│
└── export/                           [PUBLIC] Exporter Utilities
    ├── SvgExporter.kt                [PUBLIC] Vector SVG exporter
    └── CodeExporter.kt               [PUBLIC] Standalone Compose code generator
```

---

## 🏃 Running Showcase Applications

Run demo applications across platforms using Gradle commands:

- **Android App**: `./gradlew :androidApp:assembleDebug`
- **Desktop (JVM)**: `./gradlew :desktopApp:run`
- **Web (Wasm)**: `./gradlew :webApp:wasmJsBrowserDevelopmentRun`
- **Web (JS)**: `./gradlew :webApp:jsBrowserDevelopmentRun`

---

## 📄 License

```
MIT License

Copyright (c) 2026 Meet

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIMBBLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```