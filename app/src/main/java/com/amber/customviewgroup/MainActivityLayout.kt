package com.amber.customviewgroup

import android.content.Context
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.max

/**
 * Created by chenshuai on 2021/8/17
 */
class MainActivityLayout(context: Context) : CustomViewGroup(context) {

    val header = AppCompatImageView(context).apply {
        setImageResource(R.drawable.ic_launcher_background)
        scaleType = ImageView.ScaleType.CENTER_CROP
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 200.dp)
        addView(this)
    }

    val avatar = AppCompatImageView(context).apply {
        setImageResource(R.mipmap.ic_launcher)
        layoutParams = MarginLayoutParams(50.dp, 50.dp).also {
            it.topMargin = 16.dp
        }
        addView(this)
    }

    val title = AppCompatTextView(context).apply {
        text = "This is title"
        maxLines = 2
        ellipsize = TextUtils.TruncateAt.END
        textSize = 18f.sp
        setTextColor(resources.getColor(R.color.black))
        layoutParams =
            MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).also {
                it.marginStart = 8.dp
                it.marginEnd = 8.dp
            }
        addView(this)
    }

    val des = AppCompatTextView(context).apply {
        text = "This is title"
        maxLines = 4
        ellipsize = TextUtils.TruncateAt.END
        textSize = 16f.sp
        setTextColor(resources.getColor(android.R.color.darker_gray))
        layoutParams =
            MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).also {
                it.marginStart = 8.dp
                it.marginEnd = 8.dp
                it.topMargin = 8.dp
            }
        addView(this)
    }

    val fab = FloatingActionButton(context).apply {
        setImageResource(android.R.drawable.ic_menu_add)
        layoutParams =
            MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).also {
                it.marginEnd = 10.dp
            }
        addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        header.autoMeasure()
        avatar.autoMeasure()
        fab.autoMeasure()

        val titleWidth =
            measuredWidth - avatar.measuredWidthWithMargins - fab.measuredWidthWithMargins - title.marginStart - title.marginEnd
        title.measure(titleWidth.toExactlyMeasureSpec(), title.defaultHeightMeasureSpec(this))

        val desWidth = measuredWidth - avatar.measuredWidthWithMargins - fab.measuredWidthWithMargins - des.marginStart - des.marginEnd
        des.measure(desWidth.toExactlyMeasureSpec(), des.defaultHeightMeasureSpec(this))

        val contentHeight = max(avatar.marginTop + title.measuredHeight + des.measuredHeightWithMargins, avatar.measuredHeightWithMargins)

        setMeasuredDimension(measuredWidth, header.measuredHeight + contentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        header.autoLayout(0, 0)

        fab.let {
            it.autoLayout(it.marginEnd, header.bottom - (it.measuredHeight / 2), true)
        }
    }
}