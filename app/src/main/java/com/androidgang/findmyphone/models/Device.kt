package com.androidgang.findmyphone.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable

data class Device(
    @SerializedName("userId") var userId: String = "", // Поле, по которому определяется уникальность устройства (еще не выбрано, что тут будет)
    @SerializedName("deviceId") var deviceId: String = "", // Якобы уникальный айди всех устройств на андроиде
    @SerializedName("name") var name: String = "", // Имя устройства
    @SerializedName("metrics") var metrics: ArrayList<Metrics> = arrayListOf(),
)
