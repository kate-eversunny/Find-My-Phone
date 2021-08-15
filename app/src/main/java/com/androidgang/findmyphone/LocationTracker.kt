package com.androidgang.findmyphone

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

class LocationTracker(val context: Context) {
    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationRequest: LocationRequest = LocationRequest.create()
    private var locationCallback: LocationCallback

    var location: Location? = null

    init {
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = PRIORITY_BALANCED_POWER_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (l in locationResult.locations) {
                    location = locationResult.locations[0]
                }
            }
        }

    }

    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    fun updateGps() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                if (it == null) {
                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        locationCallback,
                        Looper.getMainLooper()
                    )
                }
            }.addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result != null){
                        Log.i("AAAAA", "lat " + it.result!!.latitude.toString() + "long " +   it.result!!.longitude.toString())

                    }


                }
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}