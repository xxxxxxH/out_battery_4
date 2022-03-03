package com.abc.photo.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.abc.photo.R
import kotlinx.android.synthetic.main.activity_anim.*
import uk.co.ribot.easyadapter.EasyRecyclerAdapter

class AnimAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim)
        val data = ArrayList<Int>()
        data.add(R.mipmap.anim1)
        data.add(R.mipmap.anim3)
        data.add(R.mipmap.anim4)
        data.add(R.mipmap.anim7)
        data.add(R.mipmap.anim8)
        data.add(R.mipmap.anim10)
        data.add(R.mipmap.anim11)
        data.add(R.mipmap.anim13)
        data.add(R.mipmap.anim14)
        data.add(R.mipmap.anim18)
        data.add(R.mipmap.anim20)
        data.add(R.mipmap.anim22)
        data.add(R.mipmap.anim23)
        data.add(R.mipmap.anim24)
        data.add(R.mipmap.anim25)
        data.add(R.mipmap.anim26)
        data.add(R.mipmap.anim27)
        data.add(R.mipmap.anim28)
        data.add(R.mipmap.anim33)
        data.add(R.mipmap.anim_1)
        data.add(R.mipmap.anim_2)
        data.add(R.mipmap.anim_3)
        data.add(R.mipmap.anim_5)
        data.add(R.mipmap.anim_6)
        data.add(R.mipmap.anim_7)
        data.add(R.mipmap.anim_17)
        data.add(R.mipmap.anim_18)
        data.add(R.mipmap.anim_19)
        data.add(R.mipmap.anim_21)
        data.add(R.mipmap.anim_23)
        data.add(R.mipmap.anim_24)
        data.add(R.mipmap.anim_26)
        data.add(R.mipmap.anim_31)
        data.add(R.mipmap.anim_32)
        data.add(R.mipmap.anim_34)
        val adapter = EasyRecyclerAdapter(this,AItem::class.java,data)
        recycler.layoutManager = GridLayoutManager(this,3)
        recycler.adapter = adapter
    }
}