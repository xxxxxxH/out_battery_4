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
        val result = ArrayList<Bitmap>()
        val imgs = this.resources.assets.list("anim")
        imgs?.let {
            for (item in it) {
                val b = BitmapFactory.decodeStream(resources.assets.open("anim/$item"))
                result.add(b)
            }
        }
        val adapter = EasyRecyclerAdapter(this,AItem::class.java,result)
        recycler.layoutManager = GridLayoutManager(this,3)
        recycler.adapter = adapter
    }
}