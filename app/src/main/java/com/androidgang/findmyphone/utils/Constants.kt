package com.androidgang.findmyphone.utils

import com.androidgang.findmyphone.models.Device
import com.androidgang.findmyphone.models.Metrics
import java.text.SimpleDateFormat

object Constants {
    const val BASE_URL = "http://89.22.187.96:33080/"
    const val APPLICATION_JSON_TYPE = "application/json"

    val metrics1 = arrayListOf(
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011598L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011588L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011578L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011568L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example")
    )
    val metrics2 = arrayListOf(
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011598L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011588L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011578L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011568L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example")
    )
    val metrics3 = arrayListOf(
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011598L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011588L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011578L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example"),
        Metrics(longitude = (-90..90).random().toDouble(), latitude = (-90..90).random().toDouble(), time = 1629011568L, cellId = "example", lac = "example", rsrp = "example", rsrq = "example", sinr = "example", imsi = "example")
    )

    val devices = arrayListOf(
        Device(name = "Xiaomi POCO M3", userId = "Example", metrics = metrics1, deviceId = "Example"),
        Device(name = "Honor 9C", userId = "Example", metrics =metrics2,  deviceId ="Example"),
        Device(name = "Asus M10", userId = "Example", metrics =metrics3,  deviceId ="Example"),
    )


    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")


}