package com.abc.lib.b

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tencent.mmkv.MMKV

class IReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_PACKAGE_ADDED) {
            val data = intent.dataString.toString()
            data.let {
                if (data.contains(context!!.packageName.toString())) {
                    MMKV.defaultMMKV()!!.encode("state",true)
                }
            }
        }
    }
}