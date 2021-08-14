package com.androidgang.findmyphone.models

data class Metrics(
    var longitude: Long = 0L, // Долгота
    var latitude: Long = 0L, // Широта
    var time: Long = 0L, // Когда были сделаны замеры всех данных
    var cellId: String = "", // Идентификатор соты
    var lac: String = "", // LAC (Location Area Code)
    var rsrp: String = "", // RSRP (Reference Signal Received Power) - мощность пилотного сигнала
    var rsrq: String = "", // RSRQ (Reference Signal Received Quality)
    var sinr: String = "", // RSSNR (Reference Signal to Signal Noise Ratio) - отношение мощности пилотного сигнала к отношению сигнал/шум.
    var imsi: String = "" // International Mobile Subscriber Identity — международный идентификатор мобильного абонента
)
