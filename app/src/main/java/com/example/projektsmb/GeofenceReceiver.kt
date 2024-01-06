package com.example.projektsmb

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.i("geofence", "I'm inside")
        val geoEvent = GeofencingEvent.fromIntent(intent)
        if(geoEvent != null)
        {
            val shopName = intent.extras!!.getString("shopName")
            val lat = intent.extras?.getDouble("lat")
            val lng = intent.extras?.getDouble("lng")
            val id = intent.extras?.getInt("id")

            val notificationChannel = NotificationChannel(
                "GeofencingNotification",
                "GeofencingNotification",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            notificationChannel.description = "GeofencingNotification"
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.createNotificationChannel(notificationChannel)

            if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
            {
                Log.i("geofence", "Polecenie enter")
                var builder = NotificationCompat.Builder(context, notificationChannel.id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Witamy w $shopName id: $id")
                    .setContentText("Lat: $lat Lng: $lng, sprawdź nowe promocje. Życzymy udanych zakupów")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build()

                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("geofence", "Notyfikacja enter poszla")
                    notificationManager.notify(id!!, builder)
                }
            } else if(geoEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT)
            {
                Log.i("geofence", "Dostalem polecenie exit")
                var builder = NotificationCompat.Builder(context, notificationChannel.id)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Dziękujemy, że odwiedziłeś $shopName")
                    .setContentText("Zapraszamy ponownie")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true)
                    .build()

                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("geofence", "Notyfikacja exit poszla")
                    notificationManager.notify(id!!, builder)
                }
            }

        }else{
            Log.i("geofence", "Geo is null")
        }


    }
}