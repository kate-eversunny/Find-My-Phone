package com.androidgang.findmyphone.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidgang.findmydevice.adapters.BaseAdapterCallback
import com.androidgang.findmyphone.LocationTracker
import com.androidgang.findmyphone.R
import com.androidgang.findmyphone.adapters.DevicesAdapter
import com.androidgang.findmyphone.adapters.SpacingItemDecoration
import com.androidgang.findmyphone.databinding.FragmentMapsBinding
import com.androidgang.findmyphone.models.Device
import com.androidgang.findmyphone.models.User
import com.androidgang.findmyphone.utils.Constants
import com.androidgang.findmyphone.utils.Constants.sdf
import com.androidgang.findmyphone.utils.NetworkService
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MapsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = arguments?.getSerializable("user") as User?
        Log.d("USERHERE", user.toString())

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                val locationTracker = LocationTracker(requireContext())
                for(i in 1..50){
                    locationTracker.updateGps()
                    Log.i("AAAAA", "AAAAA")

                    delay(2000)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        binding.btnDeviceInfo.setOnClickListener(this)


        val adapter = DevicesAdapter()

        binding.rvDevices.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.rvDevices.adapter = adapter

        binding.rvDevices.addItemDecoration(SpacingItemDecoration(top = 20, bottom = 20, left = 10, right = 10))

        adapter.setList(user!!.devices)
        adapter.attachCallback(object : BaseAdapterCallback<Device>{
            override fun onItemClick(model: Device, view: View) {
                chooseDevice(model, view,  mapFragment)
            }

        })


    }

    private fun chooseDevice(model: Device, view: View, mapFragment: SupportMapFragment?){
        binding.tvDeviceName.text = model.name
        val callback = OnMapReadyCallback { googleMap ->

            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isCompassEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.setAllGesturesEnabled(true)
            googleMap.isTrafficEnabled = true
            googleMap.clear()

            model.metrics.forEach { metrics ->
                var devicePlace = LatLng(metrics.latitude.toDouble(), metrics.longitude.toDouble())
               // googleMap.addMarker(MarkerOptions().position(devicePlace).title("${model.name} metrics:\n${model.metrics}"))
                googleMap.addMarker(MarkerOptions()
                    .position(devicePlace)
                    .title("Time: ${sdf.format(Date(metrics.time.toLong()))}")
                    .snippet("${metrics.longitude} ${metrics.latitude}"))
            }

            var devicePlace = LatLng(model.metrics.last().latitude.toDouble(), model.metrics.last().longitude.toDouble())
            val cameraPosition = CameraPosition.Builder().target(devicePlace).zoom(2f).build()
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            binding.tvDeviceNameLastSeen.text = "Last seen: ${sdf.format(Date(model.metrics.last().time.toLong()))}"




        }
        mapFragment?.getMapAsync(callback)



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_device_info -> {
                Toast.makeText(requireContext(), "Here we can add some information about device/user", Toast.LENGTH_SHORT).show()
            }
        }
    }




}