# Compose Loaders 🎨✨

[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg?logo=kotlin)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose Multiplatform](https://img.shields.io/badge/Compose-Multiplatform-blue.svg?logo=jetbrains)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.coding-meet/compose-loaders.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.coding-meet/compose-loaders)
[![Live Demo](https://img.shields.io/badge/Live-Demo-0A84FF?style=flat&logo=googlechrome&logoColor=white)](https://coding-meet.github.io/compose-loaders/)

**Compose Loaders** is a lightweight, customizable, open-source **Compose Multiplatform (KMP)** library with a growing collection of handcrafted pixel loading animations across Android, iOS, Desktop (JVM), and Web (Wasm/JS).

---

## 🚀 Key Features

- 💎 **Handcrafted Pixel Animations**: Pixel-perfect loading animations designed in 5×5 and 7×7 dot matrix grids.
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
    implementation("io.github.coding-meet:compose-loaders:1.0.1")
}
```

> [!IMPORTANT]
> **Kotlin 2.0.0+ Requirement**
> This library is compiled with **Kotlin 2.0.21**. Your consuming project must use **Kotlin 2.0.0 or higher** to prevent metadata binary compatibility errors (e.g. `Class '...' was compiled with an incompatible version of Kotlin`).

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

### 5. Exporting Frames & Animated Vector SVGs

Export a single static frame or a full multi-frame **Animated SVG** with CSS `@keyframes` logic:

```kotlin
import com.meet.compose.loaders.export.SvgExporter
import com.meet.compose.loaders.presets.PixelPresets

// Static Single Frame SVG
val staticSvg = SvgExporter.exportFrameToSvg(
    animation = PixelPresets.Framer.grid5x5,
    frameIndex = 0,
    activeHex = "#6366F1",
    inactiveHex = "#18181B",
    canvasSize = 200
)

// Full Multi-Frame Animated SVG (with CSS @keyframes)
val animatedSvg = SvgExporter.exportAnimatedSvg(
    animation = PixelPresets.Framer.grid5x5,
    activeHex = "#6366F1",
    inactiveHex = "#18181B",
    canvasSize = 200,
    speedMultiplier = 1.0f
)
```

---

## 🎨 Preset Loaders Catalog

| Category | Presets |
| :--- | :--- |
| **Basic UI** | `Framer`, `Gradient`, `Target`, `Unboxing`, `DotDotDot`, `TinySpinner`, `Loading`, `Importing`, `Searching`, `Busy`, `Saving`, `Initialising`, `ClassicLoading`, `Crosshair`, `Scanner`, `Eclipse` |
| **Motion & Physics** | `Newton`, `Bounce`, `Syncing`, `Ripple`, `RollingDice`, `Tumbleweed`, `TheGreatWave`, `Heartbeat`, `Radar`, `Fire`, `PulseGrid`, `Windmill`, `Tornado`, `Sandglass` |
| **Tech & Digital** | `Rocket`, `Comet`, `Infinite`, `BarChart`, `Flash`, `LoadingGraph`, `LoadingGIFs`, `DNA`, `Matrix`, `InfinityWave`, `Helix`, `DigitalRain`, `Equalizer` |
| **Retro & Gaming** | `TheClaw`, `Galaxy`, `Sleepy`, `Pacman`, `PingPong`, `PacLoop`, `Snake`, `Tetris` |

---

## 🏗️ Library Architecture

```
com.meet.compose.loaders
├── PixelLoader.kt                    **[PUBLIC]** Core Composable entry point
│
├── model/                            **[PUBLIC]** Immutable Data Contracts
│   ├── PixelAnimation.kt             **[PUBLIC]** Frame sequence & timing specifier
│   ├── PixelGrid.kt                  **[PUBLIC]** 2D matrix boolean array wrapper
│   ├── PixelGridSize.kt              **[PUBLIC]** Sealed interface: Grid5x5, Grid7x7, Custom
│   ├── PixelPreset.kt                **[PUBLIC]** Data class for presets
│   ├── PixelColorConfig.kt           **[PUBLIC]** Color palette specifier
│   └── PixelShape.kt                 **[PUBLIC]** Dot geometry enum
│
├── presets/                          **[PUBLIC & INTERNAL]** Presets Catalog Registry
│   ├── PixelPresets.kt               **[PUBLIC]** Entry point exposing `val all: List<PixelPreset>`
│   └── internal/                     **[INTERNAL]** Categorized lazy preset stores
│
├── engine/internal/                  **[INTERNAL]** Internal Engine Components
│   └── GridMath.kt                   **[INTERNAL]** Layout & coordinate math
│
├── render/internal/                  **[INTERNAL]** Hardware Graphics Pipeline
│   └── PixelCanvasRenderer.kt        **[INTERNAL]** Compose DrawScope canvas renderer
│
└── export/                           **[PUBLIC]** Exporter Utilities
    ├── SvgExporter.kt                **[PUBLIC]** Vector SVG exporter
    └── CodeExporter.kt               **[PUBLIC]** Standalone Compose code generator
```

---

## 🏃 Running Showcase Applications

Run demo applications across platforms using Gradle commands:

- **Android App**: `./gradlew :samples:androidApp:assembleDebug`
- **Desktop (JVM)**: `./gradlew :samples:desktopApp:run`
- **Web (Wasm)**: `./gradlew :samples:webApp:wasmJsBrowserDevelopmentRun`
- **Web (JS)**: `./gradlew :samples:webApp:jsBrowserDevelopmentRun`

---

## 🙏 Inspiration

Compose Loaders is inspired by the design philosophy of [Flicker by Laurie](https://flicker.laurie.fyi/gallery).

This library is an independent Jetpack Compose Multiplatform implementation built specifically for the Compose ecosystem.

It is **not affiliated with, endorsed by, or maintained by the Flicker project or its author**.

---

## 👨‍💻 Author

Built with ❤️ by **Meet**

- 🌐 [codingmeet.com](https://codingmeet.com/)
- 🐙 [github.com/Coding-Meet](https://github.com/Coding-Meet)

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.