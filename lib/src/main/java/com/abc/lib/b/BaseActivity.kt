package com.abc.lib.b

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.facebook.FacebookSdk
import com.facebook.applinks.AppLinkData
import com.permissionx.guolindev.PermissionX
import com.tencent.mmkv.MMKV
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x

abstract class BaseActivity(layoutId: Int) : AppCompatActivity(layoutId) {

    protected var appLink: String? = null
    protected var installReferrer: String? = null
    var msgCount = 0

    private val handler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 1) {
                msgCount++
                if (msgCount == 10) {
                    closeLoading()
                    startMainActivity()
                } else {
                    if (!TextUtils.isEmpty(appLink) && !TextUtils.isEmpty(installReferrer)) {
                        closeLoading()
                        startMainActivity()
                    } else {
                        sendEmptyMessageDelayed(1, 1000)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PermissionX.init(this)
            .permissions(*BaseApplication.instance!!.getPermissions())
            .request { allGranted, _, _ ->
                if (allGranted) {
                    val intentFilter = IntentFilter()
                    intentFilter.addAction("action_download")
                    intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
                    intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                    intentFilter.addDataScheme("package")
                    registerReceiver(IReceiver(), intentFilter)
                    showLoading()
                    val request = RequestParams("https://sichuanlucking.xyz/navigation489/fb.php")
                    x.http().get(request, object : Callback.CommonCallback<String> {
                        override fun onSuccess(result: String?) {
                            result?.let {
                                FacebookSdk.setApplicationId(it)
                            } ?: kotlin.run {
                                FacebookSdk.setApplicationId("2074717252705379")
                            }
                            FacebookSdk.sdkInitialize(this@BaseActivity)
                            appLink =
                                MMKV.defaultMMKV()!!.decodeString("appLink", "AppLink is empty")
                            if (appLink == "AppLink is empty") {
                                AppLinkData.fetchDeferredAppLinkData(
                                    this@BaseActivity
                                ) {
                                    if (it != null) {
                                        appLink = it.targetUri.toString()
                                        MMKV.defaultMMKV()!!.encode("appLink", appLink)
                                    }
                                }
                            }
                            installReferrer =
                                MMKV.defaultMMKV()!!.decodeString("ref", "Referrer is empty")
                            if (installReferrer == "Referrer is empty") {
                                val client =
                                    InstallReferrerClient.newBuilder(this@BaseActivity).build()
                                client.startConnection(object :
                                    InstallReferrerStateListener {
                                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                                        try {
                                            installReferrer = client.installReferrer.installReferrer
                                            MMKV.defaultMMKV()!!.encode("ref", installReferrer)
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }

                                    override fun onInstallReferrerServiceDisconnected() {

                                    }

                                })
                            }
                            handler.sendEmptyMessage(1)
                            init()
                        }

                        override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                            FacebookSdk.setApplicationId("2074717252705379")
                            appLink =
                                MMKV.defaultMMKV()!!.decodeString("appLink", "AppLink is empty")
                            if (appLink == "AppLink is empty") {
                                AppLinkData.fetchDeferredAppLinkData(
                                    this@BaseActivity
                                ) {
                                    if (it != null) {
                                        appLink = it.targetUri.toString()
                                        MMKV.defaultMMKV()!!.encode("appLink", appLink)
                                    }
                                }
                            }
                            installReferrer =
                                MMKV.defaultMMKV()!!.decodeString("ref", "Referrer is empty")
                            if (installReferrer == "Referrer is empty") {
                                val client =
                                    InstallReferrerClient.newBuilder(this@BaseActivity).build()
                                client.startConnection(object :
                                    InstallReferrerStateListener {
                                    override fun onInstallReferrerSetupFinished(responseCode: Int) {
                                        try {
                                            installReferrer = client.installReferrer.installReferrer
                                            MMKV.defaultMMKV()!!.encode("ref", installReferrer)
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }

                                    override fun onInstallReferrerServiceDisconnected() {

                                    }

                                })
                            }
                            handler.sendEmptyMessage(1)
                        }

                        override fun onCancelled(cex: Callback.CancelledException?) {

                        }

                        override fun onFinished() {

                        }

                    })
                } else {
                    finish()
                }
            }
    }

    abstract fun init()

    abstract fun startMainActivity()

    abstract fun showLoading()

    abstract fun closeLoading()
}