package com.sunny.zyui.layout


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.sunny.zyui.R


class ZyBgLayout : ConstraintLayout {

    private var path: Path

    //    private var borderPath: Path
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
        context, attrs, defStyleAttr
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
//        borderPath = Path()
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
//        path.reset()
//        rectF.set(0f, 0f, width.toFloat(), height.toFloat())
//        path.addRoundRect(rectF, radii, Path.Direction.CW)
//        canvas.clipPath(path)
        super.draw(canvas)

//        // Draw top border with corner radius
//        if (radiusTopStart > 0 || radiusTopEnd > 0) {
//            val path = Path()
//            if (radiusTopStart > 0) {
//                path.moveTo(0f, radiusTopStart)
//                path.arcTo( RectF(0f, 0f, radiusTopStart * 2, radiusTopStart * 2), -180f, 90f)
//            } else {
//                path.moveTo(0f, 0f)
//            }
//            path.lineTo(width.toFloat(), 0f)
//            if (radiusTopEnd > 0) {
//                path.arcTo( RectF(width - radiusTopEnd * 2, 0f, width.toFloat(), radiusTopEnd * 2), -90f, 90f)
//                path.lineTo(width.toFloat(), radiusTopEnd)
//            }
//            canvas.drawPath(path, borderPaint)
//        } else {
//            canvas.drawLine(0f, 0f, width.toFloat(), 0f, borderPaint)
//        }
//
//
//        // Draw bottom border with corner radius
//        // Draw bottom border with corner radius
//        if (radiusBottomStart > 0 || radiusBottomEnd > 0) {
//            val path = Path()
//            if (radiusBottomStart > 0) {
//                path.moveTo(0f, attr.height - radiusBottomStart)
//                path.arcTo(
//                    RectF(
//                        0f,
//                        attr.height - radiusBottomStart * 2,
//                        radiusBottomStart * 2,
//                        attr.height.toFloat()
//                    ), 180f, 90f
//                )
//            } else {
//                path.moveTo(0f, attr.height.toFloat())
//            }
//            path.lineTo(attr.width.toFloat(), attr.height.toFloat())
//            if (radiusBottomEnd > 0) {
//                path.lineTo(attr.width.toFloat(), attr.height - radiusBottomEnd)
//                path.arcTo(
//                    RectF(
//                        attr.width - radiusBottomEnd * 2,
//                        attr.height - radiusBottomEnd * 2,
//                        attr.width.toFloat(),
//                        attr.height.toFloat()
//                    ), 0f, 90f
//                )
//                path.lineTo(attr.width - radiusBottomEnd, attr.height.toFloat())
//                path.arcTo(
//                    RectF(
//                        attr.width - radiusBottomEnd * 2,
//                        attr.height - radiusBottomEnd * 2,
//                        attr.width.toFloat(),
//                        attr.height - radiusBottomEnd * 2
//                    ), 90f, 90f
//                )
//            }
//            canvas.drawPath(path, borderPaint)
//        } else {
//            canvas.drawLine(
//                0f,
//                attr.height.toFloat(),
//                attr.width.toFloat(),
//                attr.height.toFloat(),
//                borderPaint
//            )
//        }


        val borderPath = Path()
        val borderMiddle = borderWidth / 2



        if (radiusTopStart > 0) {
            borderPath.arcTo(
                RectF(
                    borderMiddle,
                    borderMiddle,
                    radiusTopStart * 2 - borderMiddle, radiusTopStart * 2 - borderMiddle
                ),
                -180f,
                90f
            )
        } else {
            borderPath.moveTo(borderMiddle, borderMiddle)
            borderPath.lineTo(width.toFloat() - radiusTopEnd - borderMiddle, borderMiddle)
        }

        if (radiusTopEnd > 0) {
            borderPath.arcTo(
                RectF(
                    width - radiusTopEnd * 2 - borderMiddle,
                    borderMiddle,
                    width.toFloat() - borderMiddle,
                    radiusTopEnd * 2 - borderMiddle
                ), -90f, 90f
            )
        } else {
            borderPath.lineTo(width.toFloat() - borderMiddle, height - borderMiddle)
        }

        if (radiusBottomEnd > 0) {
            borderPath.arcTo(
                RectF(
                    width - radiusBottomEnd * 2 - borderMiddle,
                    height - radiusTopEnd * 2,
                    width.toFloat() - borderMiddle,
                    height.toFloat() - borderMiddle
                ), 0f, 90f
            )
        } else {
            borderPath.lineTo(width.toFloat() - borderMiddle, height.toFloat() - borderMiddle)
        }


        if (radiusBottomStart > 0) {
            borderPath.arcTo(
                RectF(
                    borderMiddle,
                    height - radiusBottomStart * 2 - borderMiddle,
                    radiusBottomStart * 2 - borderMiddle,
                    height - borderMiddle
                ), 90f, 90f
            )
        } else {
            borderPath.lineTo(borderMiddle, height - borderMiddle)
        }

        borderPath.close()
        canvas.drawPath(borderPath, borderPaint)

    }
}