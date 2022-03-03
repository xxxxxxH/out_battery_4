package com.abc.photo.ui

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import com.abc.photo.R
import com.tencent.mmkv.MMKV
import me.panpf.sketch.SketchImageView
import org.greenrobot.eventbus.EventBus
import uk.co.ribot.easyadapter.ItemViewHolder
import uk.co.ribot.easyadapter.PositionInfo
import uk.co.ribot.easyadapter.annotations.LayoutId
import uk.co.ribot.easyadapter.annotations.ViewId

@SuppressLint("NonConstantResourceId")
@LayoutId(R.layout.layout_item_bg)
class BgItem(view: View) : ItemViewHolder<String>(view) {

    @ViewId(R.id.item_img)
    lateinit var image: SketchImageView
    @ViewId(R.id.selected)
    lateinit var iv:ImageView
    override fun onSetValues(item: String?, positionInfo: PositionInfo?) {
        val url = MMKV.defaultMMKV()!!.decodeString("bg")
        url?.let {
            if (it == item){
                iv.visibility = View.VISIBLE
            }else{
                iv.visibility = View.GONE
            }
        }
        image.displayImage(item)
        image.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("bg",item))
        }
    }
}