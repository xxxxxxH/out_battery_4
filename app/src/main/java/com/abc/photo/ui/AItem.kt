package com.abc.photo.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import com.abc.photo.R
import com.bumptech.glide.Glide
import org.greenrobot.eventbus.EventBus
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@SuppressLint("NonConstantResourceId")
@LayoutId(R.layout.layout_item_anim)
class AItem(view: View) : ItemViewHolder<Bitmap>(view) {
    @ViewId(R.id.item_img)
    lateinit var img: ImageView
    override fun onSetValues(item: Bitmap?, positionInfo: PositionInfo?) {
        Glide.with(context).load(item).into(img)
        img.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("anim", item))
        }
    }
}