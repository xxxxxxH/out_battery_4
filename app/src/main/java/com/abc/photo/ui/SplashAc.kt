package com.abc.photo.ui

import android.content.Intent
import com.abc.lib.b.BaseActivity
import com.abc.photo.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashAc : BaseActivity(R.layout.activity_splash) {

    override fun init() {
        startService(Intent(this,IService::class.java))
    }

    override fun startMainActivity() {
        startActivity(Intent(this, MainAct::class.java))
        finish()
    }

    override fun showLoading() {
        loading.start()
    }

    override fun closeLoading() {
//        loading.stop()
    }
}