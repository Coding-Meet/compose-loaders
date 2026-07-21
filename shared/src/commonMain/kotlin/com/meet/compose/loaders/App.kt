package com.meet.compose.loaders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.meet.compose.loaders.navigation.ScreenRoute
import com.meet.compose.loaders.presets.PixelPresets
import com.meet.compose.loaders.theme.AppTheme
import com.meet.compose.loaders.ui.GalleryScreen
import com.meet.compose.loaders.ui.StudioDetailScreen

@Composable
fun App() {
    var isDarkTheme by remember { mutableStateOf(true) }
    val navController = rememberNavController()

    AppTheme(isDarkTheme = isDarkTheme) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ScreenRoute.Gallery,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable<ScreenRoute.Gallery> {
                    GalleryScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme },
                        onSelectPreset = { preset ->
                            navController.navigate(ScreenRoute.StudioDetail(presetId = preset.id))
                        }
                    )
                }

                composable<ScreenRoute.StudioDetail> { backStackEntry ->
                    val route = backStackEntry.toRoute<ScreenRoute.StudioDetail>()
                    val preset = PixelPresets.all.find { it.id == route.presetId } ?: PixelPresets.Framer
                    StudioDetailScreen(
                        preset = preset,
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme },
                        onBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}