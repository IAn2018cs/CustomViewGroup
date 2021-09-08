package com.amber.customviewgroup

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

/**
 * Created by chenshuai on 2021/9/5
 */
@SuppressLint("SetTextI18n")
class CalculatorLayout(context: Context, /* 加上 AttributeSet 参数可以让 Android Studio 支持预览，不过效果不好 */ attrs: AttributeSet? = null) : CustomViewGroup(context,attrs) {

    val etResult = AppCompatEditText(context).apply {
        typeface = ResourcesCompat.getFont(context, R.font.comfortaa_regular)
        setTextColor(ResourcesCompat.getColor(resources, R.color.white, null))
        background = null
        textSize = 65f
        gravity = Gravity.BOTTOM or Gravity.END
        maxLines = 1
        isFocusable = false
        isCursorVisible = false
        setPadding(16.dp, paddingTop, 16.dp, paddingBottom)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(this)
    }

    val keyboardBackgroundView = View(context).apply {
        background = ResourcesCompat.getDrawable(resources, R.drawable.shape_cal_btn_bg, null)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        setPadding(12.dp, 12.dp, 12.dp, 12.dp)
        addView(this)
    }

    @SuppressLint("ViewConstructor")
    class NumButton(context: Context, text: String, parent: ViewGroup) : AppCompatButton(context.toTheme(R.style.Theme_CalBtn), null, androidx.appcompat.R.attr.buttonStyle) {
        init {
            setText(text)
            layoutParams =
                MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftMargin = 3.dp
                    rightMargin = 3.dp
                    topMargin = 3.dp
                    bottomMargin = 3.dp
                }
            parent.addView(this)
        }
    }

    @SuppressLint("ViewConstructor")
    class OperatorButton(context: Context, text: String, parent: ViewGroup) : AppCompatButton(context.toTheme(R.style.Theme_StyleCalOperator), null, androidx.appcompat.R.attr.buttonStyle) {
        init {
            setText(text)
            layoutParams =
                MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
                    leftMargin = 3.dp
                    rightMargin = 3.dp
                }
            parent.addView(this)
        }
    }

    val btn0 = NumButton(context, "0", this)
    val btn1 = NumButton(context, "1", this)
    val btn2 = NumButton(context, "2", this)
    val btn3 = NumButton(context, "3", this)
    val btn4 = NumButton(context, "4", this)
    val btn5 = NumButton(context, "5", this)
    val btn6 = NumButton(context, "6", this)
    val btn7 = NumButton(context, "7", this)
    val btn8 = NumButton(context, "8", this)
    val btn9 = NumButton(context, "9", this)
    val btnDot = NumButton(context, ".", this)
    val btnEqual = NumButton(context, "=", this).apply {
        background =
            ResourcesCompat.getDrawable(resources, R.drawable.ripple_cal_btn_equal, null)
    }
    val btnDiv = OperatorButton(context, "÷", this)
    val btnMulti = OperatorButton(context, "×", this)
    val btnSub = OperatorButton(context, "-", this)
    val btnAdd = OperatorButton(context, "+", this)
    val btnDel = OperatorButton(context, "DEL", this).apply {
        textSize = 18f
    }

    init {
        background = ResourcesCompat.getDrawable(resources, R.drawable.shape_cal_bg, null)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 先算一下数字按钮的大小
        val allSize =
            measuredWidth - keyboardBackgroundView.paddingLeft - keyboardBackgroundView.paddingRight -
                    btn0.marginLeft * 8
        val numBtSize = (allSize * (1 / 3.8)).toInt()
        // 计算操作按钮的大小
        val operatorBtWidth = (allSize * (0.8 / 3.8)).toInt()
        val operatorBtHeight = (numBtSize * 4 + btn0.marginTop * 6 - btnDel.marginTop * 8) / 5

        // 再计算数字盘的高度
        val keyboardHeight =
            keyboardBackgroundView.paddingTop + keyboardBackgroundView.paddingBottom +
                    numBtSize * 4 + btn0.marginTop * 8

        // 最后把高度剩余空间都给 EditText
        val editTextHeight = measuredHeight - keyboardHeight

        // 测量背景
        keyboardBackgroundView.measure(
            keyboardBackgroundView.defaultWidthMeasureSpec(this),
            keyboardHeight.toExactlyMeasureSpec()
        )

        // 测量按钮
        btn0.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn1.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn2.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn3.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn4.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn5.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn6.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn7.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn8.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btn9.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btnDot.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btnEqual.measure(numBtSize.toExactlyMeasureSpec(), numBtSize.toExactlyMeasureSpec())
        btnDiv.measure(operatorBtWidth.toExactlyMeasureSpec(), operatorBtHeight.toExactlyMeasureSpec())
        btnMulti.measure(operatorBtWidth.toExactlyMeasureSpec(), operatorBtHeight.toExactlyMeasureSpec())
        btnSub.measure(operatorBtWidth.toExactlyMeasureSpec(), operatorBtHeight.toExactlyMeasureSpec())
        btnAdd.measure(operatorBtWidth.toExactlyMeasureSpec(), operatorBtHeight.toExactlyMeasureSpec())
        btnDel.measure(operatorBtWidth.toExactlyMeasureSpec(), operatorBtHeight.toExactlyMeasureSpec())

        // 测量 EditText
        etResult.measure(
            etResult.defaultWidthMeasureSpec(this),
            editTextHeight.toExactlyMeasureSpec()
        )

        // 最后设置自己的宽高
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // 好了，测量都完了，就一个个放吧
        // 先放 EditText
        etResult.autoLayout()

        // 把背景放上
        keyboardBackgroundView.autoLayout(0, etResult.bottom)

        // 开始放按钮吧
        btn7.let {
            it.autoLayout(
                keyboardBackgroundView.paddingLeft + it.marginLeft,
                keyboardBackgroundView.top + keyboardBackgroundView.paddingTop + it.marginTop
            )
        }
        btn8.let {
            it.autoLayout(
                btn7.right + btn7.marginRight + it.marginLeft,
                btn7.top
            )
        }
        btn9.let {
            it.autoLayout(
                btn8.right + btn8.marginRight + it.marginLeft,
                btn7.top
            )
        }
        btn4.let {
            it.autoLayout(
                btn7.left,
                btn7.bottom + btn7.marginBottom + it.marginTop
            )
        }
        btn5.let {
            it.autoLayout(
                btn4.right + btn4.marginRight + it.marginLeft,
                btn4.top
            )
        }
        btn6.let {
            it.autoLayout(
                btn5.right + btn5.marginRight + it.marginLeft,
                btn4.top
            )
        }
        btn1.let {
            it.autoLayout(
                btn7.left,
                btn4.bottom + btn4.marginBottom + it.marginTop
            )
        }
        btn2.let {
            it.autoLayout(
                btn1.right + btn1.marginRight + it.marginLeft,
                btn1.top
            )
        }
        btn3.let {
            it.autoLayout(
                btn2.right + btn2.marginRight + it.marginLeft,
                btn1.top
            )
        }
        btn0.let {
            it.autoLayout(
                btn7.left,
                btn1.bottom + btn1.marginBottom + it.marginTop
            )
        }
        btnDot.let {
            it.autoLayout(
                btn0.right + btn0.marginRight + it.marginLeft,
                btn0.top
            )
        }
        btnEqual.let {
            it.autoLayout(
                btnDot.right + btnDot.marginRight + it.marginLeft,
                btn0.top
            )
        }
        btnDel.let {
            it.autoLayout(
                btn9.right + btn9.marginRight + it.marginLeft,
                btn7.top
            )
        }
        btnDiv.let {
            it.autoLayout(
                btnDel.left,
                btnDel.bottom + btnDel.marginBottom + it.marginTop
            )
        }
        btnMulti.let {
            it.autoLayout(
                btnDel.left,
                btnDiv.bottom + btnDiv.marginBottom + it.marginTop
            )
        }
        btnSub.let {
            it.autoLayout(
                btnDel.left,
                btnMulti.bottom + btnMulti.marginBottom + it.marginTop
            )
        }
        btnAdd.let {
            it.autoLayout(
                btnDel.left,
                btnSub.bottom + btnSub.marginBottom + it.marginTop
            )
        }
    }
}