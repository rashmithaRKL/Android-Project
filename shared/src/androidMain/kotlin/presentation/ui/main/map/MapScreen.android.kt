package presentation.ui.main.map

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.ui.Alignment
import android.location.Location
import android.location.LocationManager
import android.content.Context

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun MapScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    
    // RK Software Technologies location
    val defaultLocation = LatLng(6.832674902006931, 80.04284725231136)
    
    // State for camera position
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 15f)
    }
    
    // State for user's current location
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    
    // Store locations (in this case, just RK Software Technologies)
    val storeLocations = remember {
        listOf(
            LatLng(6.832674902006931, 80.04284725231136) // RK Software Technologies
        )
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                val lastKnownLocation: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                lastKnownLocation?.let {
                    userLocation = LatLng(it.latitude, it.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        LatLng(it.latitude, it.longitude),
                        15f
                    )
                }
            } catch (e: SecurityException) {
                // Handle permission denial
            }
        }
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("RK Software Technologies") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            if (locationPermissions.allPermissionsGranted) {
                FloatingActionButton(
                    onClick = {
                        userLocation?.let {
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
                        }
                    }
                ) {
                    Icon(Icons.Default.MyLocation, contentDescription = "My Location")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (!locationPermissions.allPermissionsGranted) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Location permission is required to show the store location",
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { locationPermissions.launchMultiplePermissionRequest() }) {
                        Text("Grant Permission")
                    }
                }
            } else {
                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = MapProperties(isMyLocationEnabled = true)
                ) {
                    // Add marker for RK Software Technologies
                    Marker(
                        state = MarkerState(position = defaultLocation),
                        title = "RK Software Technologies",
                        snippet = "Software Development Company"
                    )
                }
            }
        }
    }
}
