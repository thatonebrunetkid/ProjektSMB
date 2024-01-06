package com.example.projektsmb

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.projektsmb.ui.theme.ProjektSMBTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class BigMapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                    val criteria = Criteria()
                    criteria.isAltitudeRequired = true
                    criteria.accuracy = Criteria.ACCURACY_FINE
                    criteria.powerRequirement = Criteria.NO_REQUIREMENT
                    val provider = locationManager.getBestProvider(criteria, false)
                    var currentPosition : LatLng? = null
                    if(ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
                    )
                    {
                        val location = locationManager.getLastKnownLocation(provider.toString())!!
                        currentPosition = LatLng(location.latitude, location.longitude)
                    }

                    val cameraPositionState = rememberCameraPositionState{
                        position = CameraPosition.fromLatLngZoom(currentPosition!!, 15f)
                    }

                    val viewModel by viewModels<LocationDataViewModel>()
                    val positionList = viewModel.allLocations.collectAsState(initial = emptyList())

                    Column(modifier = Modifier.fillMaxSize()){
                        GoogleMap (
                            modifier = Modifier.fillMaxSize(),
                            cameraPositionState = cameraPositionState
                        ){
                            positionList.value.forEach { e ->
                                Marker(
                                    state = MarkerState(position = LatLng(e.lat, e.lng)),
                                    title = e.shopName
                                )
                            }
                        }
                        }
                    }



                }
            }
        }


}