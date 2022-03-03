package com.abc.photo.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import org.greenrobot.eventbus.EventBus

class IIReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (it.action == Intent.ACTION_BATTERY_CHANGED) {
                val scale = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                val rawLevel = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val percentage = rawLevel.toFloat() / scale.toFloat()
                val level = (percentage * 100).toInt()
                EventBus.getDefault().post(MessageEvent("b_c", level))
            } else if (it.action == Intent.ACTION_POWER_CONNECTED) {
                EventBus.getDefault().post(MessageEvent("p_c"))
            }
        }
    }
}