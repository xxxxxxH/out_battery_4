package com.abc.photo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abc.photo.R
import com.bumptech.glide.Glide
import com.tencent.mmkv.MMKV
import es.dmoral.prefs.Prefs
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_float.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
//        Assist.xxxxxxH(this)
//        waveLoadingView.centerTitle = "center title"
//        waveLoadingView.progressValue = 50
//        waveLoadingView.setAnimDuration(3000)
//        waveLoadingView.startAnimation()
        val url = Prefs.with(this).read("bg", "https://ancolorcheck.xyz/preview/img/bg_1.jpg")
        image.displayImage(url)
        val a = Prefs.with(this).readInt("anim", R.mipmap.anim1)
        anim.options.isDecodeGifImage = true
        Glide.with(this).load(a).into(anim)
        background.setOnClickListener {
            startActivity(Intent(this, BackgroundAct::class.java))
        }
        gallery.setOnClickListener {

        }
        animation.setOnClickListener {
            startActivity(Intent(this, AnimAct::class.java))
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: MessageEvent) {
        val msg = e.getMessage()
        when (msg[0]) {
            "bg" -> {
                image.displayImage(msg[1].toString())
                MMKV.defaultMMKV()!!.encode("bg",msg[1].toString())
            }
            "anim" -> {
                Glide.with(this).load(msg[1]).into(anim)
                MMKV.defaultMMKV()!!.encode("anim",msg[1] as Int)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}