package com.sunny.zyui.fun1

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.sunny.zyui.R

/**
 * Desc 自定义滑动解锁View
 * Author ZY
 * Mail sunnyfor98@gmail.com
 * Date 2021/5/17 11:52
 */
@SuppressLint("ClickableViewAccessibility")
class SlideSwitchButton : ViewGroup {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr, 0
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    var duration = 300

    var isOpen = false

    var scrollView: ScrollView? = null

    var onSwitchListener: ((isOpen: Boolean) -> Unit)? = null

    private var itemHeight = 0
    private var itemPadding = 0
    private var parentWidth = 0

    private val stopImgView: ImageView by lazy {
        ImageView(context).apply {
            setImageResource(R.drawable.f1_svg_btn_stop)
        }
    }

    private val startImgView: ImageView by lazy {
        ImageView(context).apply {
            setImageResource(R.drawable.f1_svg_btn_start)
        }
    }

    private val hintView: TextView by lazy {
        TextView(context).apply {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.dp_14))
            compoundDrawablePadding = resources.getDimension(R.dimen.dp_5).toInt()
            setTextColor(Color.parseColor("#727b9f"))
        }
    }

    init {
        setBackgroundResource(R.drawable.f1_sel_bg_slide_btn)
        addView(hintView)
        updateHint()

        addView(stopImgView)
        addView(startImgView)

        var x = 0
        startImgView.setOnTouchListener { v, event ->

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    scrollView?.requestDisallowInterceptTouchEvent(true)
                    x = event.x.toInt()
                }

                MotionEvent.ACTION_UP -> {

                    if (startImgView.x < (parentWidth - startImgView.width) / 2) {
                        play(false)
                    } else {
                        play(true)
                    }

                    scrollView?.requestDisallowInterceptTouchEvent(false)
                }
                MotionEvent.ACTION_MOVE -> {
                    val lastX = event.x - x
                    if (startImgView.x + lastX > parentWidth - itemPadding - startImgView.width) {
                        return@setOnTouchListener true
                    }

                    if (startImgView.x + lastX < itemPadding) {
                        return@setOnTouchListener true
                    }
                    startImgView.x += lastX
                }
            }

            return@setOnTouchListener true
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, resources.getDimension(R.dimen.dp_90).toInt())
        itemPadding = resources.getDimension(R.dimen.dp_5).toInt()
        itemHeight = resources.getDimension(R.dimen.dp_80).toInt()
        parentWidth = MeasureSpec.getSize(widthMeasureSpec)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        stopImgView.layout(
            itemPadding,
            itemPadding,
            itemPadding + itemHeight,
            itemPadding + itemHeight
        )

        startImgView.layout(
            itemPadding,
            itemPadding,
            itemPadding + itemHeight,
            itemPadding + itemHeight
        )

        val len =
            hintView.paint.measureText(hintView.text.toString()) + resources.getDimension(R.dimen.dp_24)
        val let = (r - len) / 2
        hintView.layout(
            let.toInt(),
            resources.getDimension(R.dimen.dp_35).toInt(),
            (let + len).toInt(),
            resources.getDimension(R.dimen.dp_55).toInt()
        )
    }


    /**
     * flag tue为开始 false为停止
     */
    private fun play(flag: Boolean) {
        val mStart = startImgView.x
        val mEnd = if (flag) {
            parentWidth - itemPadding * 2 - startImgView.width.toFloat()
        } else {
            stopImgView.x - itemPadding
        }

        val animatorOBJ =
            ObjectAnimator.ofFloat(startImgView, "translationX", mStart, mEnd)
        animatorOBJ.duration = duration.toLong()
        animatorOBJ.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                updateHint(flag)
                if (flag != isOpen) {
                    isOpen = flag
                    onSwitchListener?.invoke(flag)
                }
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
        animatorOBJ.start()
    }

    private fun updateHint(lock: Boolean = false) {
        val icon = if (lock) {
            hintView.text = "滑动停止"
            ResourcesCompat.getDrawable(resources, R.drawable.f1_svg_left_arrow, null)
        } else {
            hintView.text = "滑动开始"
            ResourcesCompat.getDrawable(resources, R.drawable.f1_svg_right_arrow, null)
        }
        icon?.setBounds(
            0,
            0,
            resources.getDimension(R.dimen.dp_14).toInt(),
            resources.getDimension(R.dimen.dp_12).toInt()
        )
        if (lock) {
            hintView.setCompoundDrawables(icon, null, null, null)
        } else {
            hintView.setCompoundDrawables(null, null, icon, null)
        }
    }


    fun stop() {
        play(false)
    }


    fun start() {
        play(true)
    }
}