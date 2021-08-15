package com.androidgang.findmyphone.utils.metrics

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.telephony.*
import android.util.Log
import com.androidgang.findmyphone.models.Device
import com.androidgang.findmyphone.models.Metrics
import java.lang.Exception

interface MetricUtils {


    @SuppressLint("HardwareIds")
    fun addMetricsToDevice(device: Device, context: Context){
        val metrics = Metrics()
        try {
            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val cellLocation = telephonyManager.allCellInfo
            val info = cellLocation.firstOrNull()
            when (info) {
                is CellInfoLte -> {
                    Log.i("TelephonyManagerLog", "CellInfoLte is reached")
                    metrics.rsrp = info.cellSignalStrength.rsrp.toString()
                    metrics.rsrq = info.cellSignalStrength.rsrq.toString()
                    metrics.sinr = info.cellSignalStrength.rssnr.toString()
                    metrics.lac = info.cellIdentity.tac.toString()

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        device.deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);
                    } else {
                        device.deviceId = telephonyManager.imei.toString()
                    }

                    metrics.cellId = info.cellIdentity.ci.toString()
                    Log.i("TelephonyManagerLog", "Models after CellInfoLte:\n ${metrics.toString()} ")
                }
                is CellInfoGsm -> {
                    Log.i("TelephonyManagerLog", "CellInfoGsm is reached")
                    val identityGsm = info.cellIdentity
                    metrics.lac = identityGsm.lac.toString()
                    metrics.cellId = identityGsm.cid.toString()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        device.deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);
                    } else {
                        device.deviceId = telephonyManager.imei.toString()

                    }
                    Log.i("TelephonyManagerLog", "Models after CellInfoGsm:\n ${metrics.toString()} ")
                }
                is CellInfoCdma -> {
                    Log.i("TelephonyManagerLog", "CellInfoCdma is reached")
                    val identityCdma = info.cellIdentity
                    metrics.lac = identityCdma.basestationId.toString()
                    device.deviceId = telephonyManager.meid.toString()

                    Log.i("TelephonyManagerLog", "Models after CellInfoCdma:\n ${metrics.toString()} ")
                }
                is CellInfoWcdma -> {
                    Log.i("TelephonyManagerLog", "CellInfoWcdma is reached")
                    val identityWcdma = info.cellIdentity
                    metrics.lac = identityWcdma.lac.toString()
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        device.deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID);
                    } else {
                        device.deviceId = telephonyManager.meid.toString()

                    }
                    metrics.cellId = identityWcdma.cid.toString()
                    Log.i("TelephonyManagerLog", "Models after CellInfoWcdma:\n ${metrics.toString()} ")
                }
            }

            try {
                metrics.imsi = telephonyManager.subscriberId.toString() // Todo to request permissions later
                Log.i("TelephonyManagerLog", "Imsi is got!")
            } catch (e: Exception) {
                metrics.imsi = ""
            }
            Log.i("TelephonyManagerLog", "Finally, We did it!\n ${metrics} ")


            device.metrics.add(metrics)

        } catch (e: Exception) {
            Log.e("TelephonyManagerLog", "Unable to obtain cell signal information", e)
        }
    }
}