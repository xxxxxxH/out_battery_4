package com.abc.photo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abc.lib.b.Assist
import com.abc.photo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Assist.xxxxxxH(this)
    }
}