package com.abc.photo.ui

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder

class IService :Service(){

    private val r = IIReceiver()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    override fun onCreate() {
        super.onCreate()
        val intent = IntentFilter()
        intent.addAction(Intent.ACTION_BATTERY_CHANGED)
        intent.addAction(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(r,intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(r)
    }
}