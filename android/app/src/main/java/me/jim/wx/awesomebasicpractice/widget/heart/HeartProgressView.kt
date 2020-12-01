package me.jim.wx.awesomebasicpractice.widget.heart

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Choreographer
import android.widget.FrameLayout
import me.jim.wx.awesomebasicpractice.util.dp2Px
import me.jim.wx.awesomebasicpractice.util.getScreenWidth
import java.util.*

class HeartProgressView : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private val mEvaluator = BezierEvaluator(getScreenWidth(context).toFloat(), dp2Px(context, 120))
    private val mPaint = Paint()
    private val mPath = Path()

    init {
        mPaint.color = Color.RED
        mPaint.strokeWidth = 2F
        mPaint.style = Paint.Style.FILL

        setOnClickListener {
            mEvaluator.invalidate()
            invalidate()
            mPath.reset()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPath.moveTo(0F, (height/2.0).toFloat())
        canvas.drawColor(Color.WHITE)

        for (i in 1..100) {
            val evaluate = mEvaluator.evaluate(i / 100.0f)
            mPath.lineTo(evaluate.x, evaluate.y)
        }
        mPath.lineTo(width.toFloat(), height.toFloat())
        mPath.lineTo(0F,height.toFloat())
        mPath.lineTo(0F, (height /2).toFloat())

        canvas.drawPath(mPath, mPaint)

        Choreographer.getInstance().postFrameCallbackDelayed( {
            mEvaluator.invalidate()
            invalidate()
            mPath.reset()
        }, 100)
    }
}

class BezierEvaluator(val width: Float, val height: Float) {
    private val cp = Array(4) {
        PointF()
    }
    private val random = Random()

    init {
        cp[0] = PointF(0f, height / 2.0f)
        cp[1] = PointF(width / 4.0f, height - random.nextInt((height / 4.0f).toInt()))
        cp[2] = PointF(width / 4.0f * 3, random.nextInt((height / 4.0f).toInt()).toFloat())
        cp[3] = PointF(width.toFloat(), height / 2.0f)
    }

    fun evaluate(fraction: Float): PointF {
        return pointOnCubicBezier(cp, fraction)
    }

    /*
     * cp 在此是四个元素的数组: cp[0] 为起点，或上图中的 P0 cp[1] 为第一控制点，或上图中的 P1 cp[2]
     * 为第二控制点，或上图中的 P2 cp[3] 为结束点，或上图中的 P3 t 为参数值，0 <= t <= 1
     */
    private fun pointOnCubicBezier(cp: Array<PointF>, t: Float): PointF {
        val ax: Float
        val bx: Float
        val cx: Float
        val ay: Float
        val by: Float
        val cy: Float
        val tSquared: Float
        val tCubed: Float
        val result = PointF()
        /* 计算多项式系数 */cx = 3.0f * (cp[1].x - cp[0].x)
        bx = 3.0f * (cp[2].x - cp[1].x) - cx
        ax = cp[3].x - cp[0].x - cx - bx
        cy = 3.0f * (cp[1].y - cp[0].y)
        by = 3.0f * (cp[2].y - cp[1].y) - cy
        ay = cp[3].y - cp[0].y - cy - by
        /* 计算t位置的点值 */tSquared = t * t
        tCubed = tSquared * t
        result.x = ax * tCubed + bx * tSquared + cx * t + cp[0].x
        result.y = ay * tCubed + by * tSquared + cy * t + cp[0].y
        return result
    }

    fun invalidate() {
        cp[1].y = random.nextInt((height).toInt()).toFloat()
        cp[2].y = random.nextInt((height).toInt()).toFloat()
    }
}