package com.abc.lib.b

import android.app.Application
import cn.finalteam.okhttpfinal.OkHttpFinal
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration
import com.tencent.mmkv.MMKV
import org.xutils.x


abstract class BaseApplication : Application() {

    companion object {
        var instance: BaseApplication? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
        x.Ext.init(this)
        val builder = OkHttpFinalConfiguration.Builder()
        OkHttpFinal.getInstance().init(builder.build())
    }

    abstract fun getAppId(): String
    abstract fun getAppName(): String
    abstract fun getUrl(): String
    abstract fun getAesPassword(): String
    abstract fun getAesHex(): String
    abstract fun getToken(): String
    abstract fun getPermissions(): Array<String>

}