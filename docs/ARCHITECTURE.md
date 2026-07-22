# Showcase App Architecture

The showcase app within the `:samples:shared` module is structured cleanly into screen-specific feature subfolders following an MVI (Model-View-Intent) architecture:

```
samples/shared/src/commonMain/kotlin/com/meet/compose/loaders/ui
├── App.kt                          # Main App layout and Navigation
│
├── common/                         # Shared UI Components across screens
│   ├── ColorPickerDialog.kt        # Color selection picker dialog
│   ├── ColorSwatch.kt              # Grid pixel color selection swatch
│   └── DynamicCustomColorSwatch.kt # Advanced customizable color swatch
│
├── gallery/                        # Gallery Screen feature folder
│   ├── GalleryContract.kt          # MVI contracts (UiState, Intent) for Gallery
│   ├── GalleryScreen.kt            # Showcase grid of all preset loaders
│   ├── GalleryViewModel.kt         # MVI ViewModel managing Gallery State
│   └── components/
│       ├── FilterChip.kt           # Speed control multiplier toggles
│       └── PresetGalleryCard.kt    # Individual loader card preview
│
└── studio/                         # Studio Detail workspace screen feature folder
    ├── StudioDetailContract.kt     # MVI contracts (UiState, Intent) for Studio
    ├── StudioDetailScreen.kt       # Live customization & code/SVG export panel
    ├── StudioDetailViewModel.kt    # MVI ViewModel managing Studio Detail State
    └── components/
        ├── GridSizeOptionButton.kt # Resolution grid sizes toggles (5×5 / 7×7)
        └── OptionBadge.kt          # Badges for size & speed option buttons
```

## MVI Pattern

Each screen follows a strict MVI pattern:

| Layer | File | Responsibility |
| :--- | :--- | :--- |
| **Contract** | `*Contract.kt` | Defines immutable `UiState` data class and `Intent` sealed interface |
| **ViewModel** | `*ViewModel.kt` | Exposes `StateFlow<UiState>` and processes intents via `onIntent(intent)` |
| **Screen** | `*Screen.kt` | Observes `uiState` via `collectAsState()`, dispatches user actions as intents |

### Example Flow

```
User taps a color swatch
  → Screen dispatches: viewModel.onIntent(GalleryIntent.SelectActiveColor(color))
  → ViewModel reduces: _uiState.update { it.copy(selectedActiveColor = color) }
  → Screen recomposes: val uiState by viewModel.uiState.collectAsState()
```
