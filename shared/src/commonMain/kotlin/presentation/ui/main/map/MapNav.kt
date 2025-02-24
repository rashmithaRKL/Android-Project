package presentation.ui.main.map

import androidx.compose.runtime.Composable
import com.razzaghi.shopingbykmp.android.map.MapScreen

@Composable
fun MapNav(
    onBackClick: () -> Unit
) {
    MapScreen(onBackClick = onBackClick)
}
