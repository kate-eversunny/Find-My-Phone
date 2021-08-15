package com.androidgang.findmyphone.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerializedName("email") var email : String = "",
    @SerializedName("devices") var devices: ArrayList<Device> = arrayListOf()
) : java.io.Serializable