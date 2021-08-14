package com.androidgang.findmyphone.fragments

import android.graphics.Color
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidgang.findmydevice.adapters.BaseAdapterCallback
import com.androidgang.findmyphone.R
import com.androidgang.findmyphone.adapters.DevicesAdapter
import com.androidgang.findmyphone.adapters.SpacingItemDecoration
import com.androidgang.findmyphone.databinding.FragmentMapsBinding
import com.androidgang.findmyphone.models.Device
import com.androidgang.findmyphone.utils.Constants
import com.androidgang.findmyphone.utils.Constants.sdf

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

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
        adapter.attachCallback(object : BaseAdapterCallback<Device>{
            override fun onItemClick(model: Device, view: View) {
                chooseDevice(model, view,  mapFragment)
            }

        })
        adapter.setList(Constants.devices)

    }

    private fun chooseDevice(model: Device, view: View, mapFragment: SupportMapFragment?){
        binding.tvDeviceName.text = model.name
        binding.tvDeviceNameLastSeen.text = "Last seen: ${sdf.format(Date(model.lastSeen))}"
        val callback = OnMapReadyCallback { googleMap ->
            googleMap.clear()
            val devicePlace = LatLng(model.latitude, model.longitude)
            googleMap.uiSettings.isZoomControlsEnabled = true
            googleMap.uiSettings.isCompassEnabled = true
            googleMap.uiSettings.isMyLocationButtonEnabled = true
            googleMap.uiSettings.setAllGesturesEnabled(true)
            googleMap.isTrafficEnabled = true
            val cameraPosition = CameraPosition.Builder().target(devicePlace).zoom(12f).build()
            googleMap.addMarker(MarkerOptions().position(devicePlace).title("Here is your device: ${model.name}"))
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
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