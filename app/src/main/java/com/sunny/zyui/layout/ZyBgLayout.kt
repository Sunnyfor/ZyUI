package com.sunny.zyui.layout


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunny.zyui.R

class ZyBgLayout : ConstraintLayout {

    private var path: Path
    private var rectF: RectF
    private var borderPaint: Paint
    private val radii = FloatArray(8)

    private var radius = 0f
    private var radiusTopStart = 0f
    private var radiusTopEnd = 0f
    private var radiusBottomStart = 0f
    private var radiusBottomEnd = 0f
    private var borderWidth = 0f
    private var borderColor = 0

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
        borderWidth = typeArray.getDimension(R.styleable.ZyBgLayout_borderWidth, 0f)
        borderColor = typeArray.getColor(R.styleable.ZyBgLayout_borderColor, 0)

        typeArray.recycle()
        borderPaint = Paint()
        borderPaint.isAntiAlias = true
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
        path = Path()
        rectF = RectF()

        if (radius > 0) {
            for (i in radii.indices) {
                radii[i] = radius
            }
        } else {
            radii[0] = radiusTopStart
            radii[1] = radiusTopStart
            radii[2] = radiusTopEnd
            radii[3] = radiusTopEnd
            radii[4] = radiusBottomEnd
            radii[5] = radiusBottomEnd
            radii[6] = radiusBottomStart
            radii[7] = radiusBottomStart

        }
        setLayerType(View.LAYER_TYPE_HARDWARE, null)

        if (background == null) {
            background = ColorDrawable(Color.TRANSPARENT)
        }
    }

    override fun draw(canvas: Canvas) {
        path.reset()
        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rectF, radii, Path.Direction.CW)
        canvas.drawFilter =
            PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG or Paint.ANTI_ALIAS_FLAG)
        canvas.clipPath(path)

        super.draw(canvas)

        if (borderWidth > 0 && borderColor != 0) {
            canvas.drawPath(path,borderPaint)
        }
    }
}