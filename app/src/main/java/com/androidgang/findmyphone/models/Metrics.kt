package com.androidgang.findmyphone.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Metrics(
    @SerializedName("longitude") var longitude: Double = 0.0, // Долгота
    @SerializedName("latitude") var latitude: Double = 0.0, // Широта
    @PrimaryKey(autoGenerate = false)
    @SerializedName("time") var time: Long = 0L, // Когда были сделаны замеры всех данных
    @SerializedName("cellId") var cellId: String = "", // Идентификатор соты
    @SerializedName("lac") var lac: String = "", // LAC (Location Area Code)
    @SerializedName("rsrp") var rsrp: String = "", // RSRP (Reference Signal Received Power) - мощность пилотного сигнала
    @SerializedName("rsrq") var rsrq: String = "", // RSRQ (Reference Signal Received Quality)
    @SerializedName("sinr") var sinr: String = "", // RSSNR (Reference Signal to Signal Noise Ratio) - отношение мощности пилотного сигнала к отношению сигнал/шум.
    @SerializedName("imsi") var imsi: String = "" // International Mobile Subscriber Identity — международный идентификатор мобильного абонента
)
