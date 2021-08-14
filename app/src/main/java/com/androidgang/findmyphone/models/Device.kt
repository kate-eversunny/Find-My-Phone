package com.androidgang.findmyphone.models

data class Device(
    var idDevice: Int = 0,
    var name: String = "",
    var lastSeen: Long = 0L,
    var longitude: Double = 0.0,
    var latitude: Double = 0.0
)
