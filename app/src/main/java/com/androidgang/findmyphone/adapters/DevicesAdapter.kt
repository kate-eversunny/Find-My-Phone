package com.androidgang.findmyphone.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androidgang.findmydevice.adapters.BaseAdapter
import com.androidgang.findmydevice.adapters.BaseViewHolder
import com.androidgang.findmyphone.R
import com.androidgang.findmyphone.models.Device

class DevicesAdapter : BaseAdapter<Device>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Device> {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        )
    }

    class ViewHolder(itemView: View) : BaseViewHolder<Device>(itemView = itemView) {
        override fun bind(model: Device) {

        }
    }
}