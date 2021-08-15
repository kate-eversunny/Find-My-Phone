package com.androidgang.findmyphone

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import com.androidgang.findmyphone.fragments.MapsFragment
import com.androidgang.findmyphone.models.User
import com.androidgang.findmyphone.utils.NetworkService
import com.androidgang.findmyphone.utils.metrics.MetricUtils
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LocationTracker(val context: Context)  : MetricUtils {
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

    fun updateGps(user: User?) {
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
                        MapsFragment.setMetrics(it.result.longitude, it.result.latitude)

                        Log.i("AAAAA", user.toString())

                        addMetricsToDevice(user!!.devices.last(), context)
                        user.devices.last().metrics.last().latitude = it.result!!.latitude
                        user.devices.last().metrics.last().longitude = it.result!!.longitude
                        user.devices.last().metrics.last().time = System.currentTimeMillis()
                        var service = NetworkService.Factory.create()
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                try {
                                    service.postMetric(user!!.email, user.devices.last())
                                    Log.i("ServiceNetwork", "EEEEEEEE")
                                } catch (e: Exception){
                                    e.printStackTrace()
                                    Log.i("ServiceNetwork", "NOOOOOOOOOOOO")
                                }

                            }
                        }


                    }


                }
            }
        }
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}