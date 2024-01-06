package com.example.projektsmb
import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import com.example.projektsmb.Data.LocationData
import com.example.projektsmb.ui.theme.ui.theme.ProjektSMBTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.activity.viewModels
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap

class AddToMapActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjektSMBTheme {
                // A surface container using the 'background' color from the theme
                SetSystemBarColor(color = Color(android.graphics.Color.parseColor("#200036")))
                var shopName = intent.extras?.getString("name")
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(Color(android.graphics.Color.parseColor("#200036")))
                            .fillMaxSize()
                            .padding(top = 30.dp)
                    ) {
                        Text(text = "Add to fav",
                            fontSize = 54.sp,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White
                            )

                        Column (modifier = Modifier.padding(all = 50.dp)){}

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .clip(shape = RoundedCornerShape(50.dp, 50.dp, 0.dp, 0.dp))
                            .background(Color.White))
                        {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                Text(text = "Map",
                                    fontSize = 32.sp,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black,
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 15.dp)
                                    )

                                Spacer(modifier = Modifier.padding(15.dp))

                                GoogleMapBoxPresentation(context = applicationContext, shopName = shopName!!)


                            }
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun GoogleMapBoxPresentation(context: Context, shopName: String)
    {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        criteria.isAltitudeRequired = true
        criteria.accuracy = Criteria.ACCURACY_FINE
        criteria.powerRequirement = Criteria.NO_REQUIREMENT
        val provider = locationManager.getBestProvider(criteria, false)
        var currentPosition : LatLng? = null
        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
            )
        {
            val location = locationManager.getLastKnownLocation(provider.toString())!!
            currentPosition = LatLng(location.latitude, location.longitude)
        }

        val cameraPositionState = rememberCameraPositionState{
            position = CameraPosition.fromLatLngZoom(currentPosition!!, 15f)
        }


        var sliderValue by remember{ mutableStateOf(100f) }
        Box(modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(250.dp)
            .clip(shape = RoundedCornerShape(50.dp, 50.dp, 50.dp, 50.dp)))
        {

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                   state = MarkerState(position = currentPosition!!),
                    title = shopName
                )
                Circle(
                    center = currentPosition!!,
                    radius = sliderValue.toDouble(),
                    strokeWidth = 5f,
                    fillColor = Color.Blue,
                )
            }

        }
        
        Spacer(modifier = Modifier.padding(10.dp))

        Slider(value = sliderValue, onValueChange = {sliderValue_ -> sliderValue = sliderValue_},
            valueRange = 100f..500f,
            steps = 3,
            modifier = Modifier.padding(start = 25.dp, end = 25.dp)
        )
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Radius " + sliderValue.toString() + "m",
                fontSize = 24.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        Spacer(modifier = Modifier.padding(top = 5.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            val geoClient = LocationServices.getGeofencingClient(context)
            Button(onClick = {
                             val locationData = LocationData(
                                 shopName = shopName,
                                 lat = currentPosition!!.latitude,
                                 lng = currentPosition!!.longitude,
                                 radius = sliderValue
                             )

                val client = LocationServices.getGeofencingClient(this@AddToMapActivity)
                val geofence = Geofence.Builder()
                    .setRequestId(locationData.id.toString())
                    .setCircularRegion(
                        locationData.lat,
                        locationData.lng,
                        locationData.radius
                    ).setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setTransitionTypes(
                        Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT
                    ).build()

                val geofenceRequest = GeofencingRequest.Builder().apply {
                    setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                    addGeofence(geofence)
                }.build()

                val intent = Intent(applicationContext, GeofenceReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, Intent.FILL_IN_DATA or PendingIntent.FLAG_MUTABLE)
                intent.putExtra("shopName", shopName)
                intent.putExtra("lat", locationData.lat)
                intent.putExtra("lng", locationData.lng)
                intent.putExtra("id", locationData.id)
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

                client.addGeofences(geofenceRequest, pendingIntent).run {
                    addOnSuccessListener {
                        Log.i("geofence", "Geo has been added")
                    }
                    addOnFailureListener{
                        Log.i("geofence", "Geo got error")
                    }
                }
                AddLocationToDatabase(locationData)
            },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.outlinedButtonColors(Color(android.graphics.Color.parseColor("#f76a54"))) )
            {
                Text(text = "Add to fav", color = Color.White)
            }
        }
    }

    private fun AddLocationToDatabase(locationData : LocationData)
    {
        val viewModel by viewModels<LocationDataViewModel>()
        viewModel.insert(locationData)
        onBackPressed()
    }



}

