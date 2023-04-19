package com.sunny.zyui.layout


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunny.zyui.R


class ZyBgLayout : ConstraintLayout {

    private var radius = 0f
    private var radiusTopStart = 0f
    private var radiusTopEnd = 0f
    private var radiusBottomStart = 0f
    private var radiusBottomEnd = 0f

    private var path: Path
    private var rectF: RectF
    private var paint: Paint

    private val radii = FloatArray(8)

    private var background: Drawable?

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        val typeArray = getContext().obtainStyledAttributes(attrs, R.styleable.ZyBgLayout)
        radius = typeArray.getDimension(R.styleable.ZyBgLayout_radius, 0f)
        radiusTopStart = typeArray.getDimension(R.styleable.ZyBgLayout_radiusTopStart, 0f)
        radiusTopEnd = typeArray.getDimension(R.styleable.ZyBgLayout_radiusTopEnd, 0f)
        radiusBottomStart = typeArray.getDimension(R.styleable.ZyBgLayout_radiusBottomStart, 0f)
        radiusBottomEnd = typeArray.getDimension(R.styleable.ZyBgLayout_radiusBottomEnd, 0f)

        background = typeArray.getDrawable(R.styleable.ZyBgLayout_android_background)
        typeArray.recycle()
        path = Path()
        rectF = RectF()
        paint = Paint()
        paint.isAntiAlias = true
//        paint.color = background

        if (radius > 0) {
            for (i in radii.indices) {
                radii[i] = radius
            }
        }

    }


    override fun onDraw(canvas: Canvas) {
        path.reset()
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rectF, radii, Path.Direction.CW)
        canvas.clipPath(path)
//        super.onDraw(canvas)

        if (background != null) {
            background?.setBounds(
                0,
                0,
                width,
                height
            )
            background?.draw(canvas)
        }else{
            canvas.drawPath(path, paint)
        }
    }
}