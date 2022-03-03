package com.abc.photo.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abc.lib.b.Assist
import com.abc.photo.R
import com.bumptech.glide.Glide
import com.lcw.library.imagepicker.ImagePicker
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
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(IIReceiver(), intentFilter)
        Assist.xxxxxxH(this)
        waveLoadingView.setAnimDuration(3000)
        waveLoadingView.startAnimation()
        val url = Prefs.with(this).read("bg", "https://ancolorcheck.xyz/preview/img/bg_1.jpg")
        image.displayImage(url)
        val a = Prefs.with(this).readInt("anim", R.mipmap.anim1)
        anim.options.isDecodeGifImage = true
        Glide.with(this).load(a).into(anim)
        background.setOnClickListener {
            startActivity(Intent(this, BackgroundAct::class.java))
            fabs_menu.collapse()
        }
        gallery.setOnClickListener {
            ImagePicker.getInstance()
                .setTitle("select")
                .showCamera(false)
                .showVideo(false)
                .showImage(true)
                .setSingleType(true)
                .setImageLoader(GlideLoader())
                .start(this, 100)
            fabs_menu.collapse()
        }
        animation.setOnClickListener {
            startActivity(Intent(this, AnimAct::class.java))
            fabs_menu.collapse()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                data?.let {
                    val url: String =
                        (it.getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES) as ArrayList<String>)[0]
                    image.displayImage(url)
                    Prefs.with(this).write("bg",url)
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(e: MessageEvent) {
        val msg = e.getMessage()
        when (msg[0]) {
            "bg" -> {
                image.displayImage(msg[1].toString())
                Prefs.with(this).write("bg",msg[1].toString())
            }
            "anim" -> {
                Glide.with(this).load(msg[1]).into(anim)
                Prefs.with(this).writeInt("anim",msg[1] as Int)
            }
            "b_c" -> {
                waveLoadingView.centerTitle = "${msg[1]}%"
                waveLoadingView.progressValue = msg[1] as Int
            }
            "p_c" -> {
                val intent = Intent(this, IngAct::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}