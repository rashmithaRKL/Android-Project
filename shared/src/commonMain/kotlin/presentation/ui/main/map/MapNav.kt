package presentation.ui.main.map

import androidx.compose.runtime.Composable
import presentation.ui.main.map.MapScreen

@Composable
fun MapNav(
    onBackClick: () -> Unit
) {
    MapScreen(onBackClick = onBackClick)
}
