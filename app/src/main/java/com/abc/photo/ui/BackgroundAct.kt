package com.abc.photo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.abc.photo.R
import kotlinx.android.synthetic.main.activity_background.*
import uk.co.ribot.easyadapter.EasyRecyclerAdapter

class BackgroundAct : AppCompatActivity() {
    private val bg = "https://ancolorcheck.xyz/preview/img/bg_*.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        val data = ArrayList<String>()
        for (index in 1..31) {
            if (index == 24)
                continue
            data.add(bg.replace("*", index.toString()))
        }
        val adapter = EasyRecyclerAdapter(this,BgItem::class.java,data)
        recycler.layoutManager = GridLayoutManager(this,2)
        recycler.adapter = adapter
    }
}