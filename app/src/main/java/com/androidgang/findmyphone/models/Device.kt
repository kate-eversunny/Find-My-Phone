package com.androidgang.findmyphone.models


data class Device(
    var name: String = "", // Имя устройства
    var userId: String = "", // Поле, по которому определяется уникальность устройства (еще не выбрано, что тут будет)
    var metrics: MutableList<Metrics> = mutableListOf<Metrics>(),
    var deviceId: String = "" // Якобы уникальный айди всех устройств на андроиде
)
