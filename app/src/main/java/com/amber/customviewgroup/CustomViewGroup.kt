package com.amber.customviewgroup

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.*

/**
 * Created by chenshuai on 2021/8/17
 */
val Int.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()

val Float.sp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP, this,
        Resources.getSystem().displayMetrics
    )

// 可以将 xml 中的 style 设置到 theme 上，这样 obtainStyledAttributes 的时候就可以使用设置的 style 了
fun Context.toTheme(@StyleRes style: Int) = ContextThemeWrapper(this, style)

abstract class CustomViewGroup(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    protected val View.measuredWidthWithMargins get() = measuredWidth + marginStart + marginEnd

    protected val View.measuredHeightWithMargins get() = measuredHeight + marginTop + marginBottom

    protected fun Int.toExactlyMeasureSpec() =
        MeasureSpec.makeMeasureSpec(this, MeasureSpec.EXACTLY)

    protected fun Int.toAtMostMeasureSpec() = MeasureSpec.makeMeasureSpec(this, MeasureSpec.AT_MOST)

    protected fun View.defaultWidthMeasureSpec(parent: ViewGroup): Int {
        return when (layoutParams.width) {
            MATCH_PARENT -> parent.measuredWidth.toExactlyMeasureSpec()
            WRAP_CONTENT -> WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalAccessException("我不考虑这种情况 $this")
            else -> layoutParams.width.toExactlyMeasureSpec()
        }
    }

    protected fun View.defaultHeightMeasureSpec(parent: ViewGroup): Int {
        return when (layoutParams.height) {
            MATCH_PARENT -> parent.measuredHeight.toExactlyMeasureSpec()
            WRAP_CONTENT -> WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalAccessException("我不考虑这种情况 $this")
            else -> layoutParams.height.toExactlyMeasureSpec()
        }
    }

    protected fun View.autoMeasure() {
        measure(
            this.defaultWidthMeasureSpec(this@CustomViewGroup),
            this.defaultHeightMeasureSpec(this@CustomViewGroup)
        )
    }

    protected fun View.autoLayout(x: Int = 0, y: Int = 0, fromRight: Boolean = false) {
        if (!fromRight) {
            layout(x, y, x + measuredWidth, y + measuredHeight)
        } else {
            autoLayout(this@CustomViewGroup.measuredWidth - x - measuredWidth, y)
        }
    }
}