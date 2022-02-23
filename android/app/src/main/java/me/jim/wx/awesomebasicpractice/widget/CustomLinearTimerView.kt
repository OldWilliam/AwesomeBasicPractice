package me.jim.wx.awesomebasicpractice.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View
import me.jim.wx.awesomebasicpractice.R

class CustomLinearTimerView : View {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @Suppress("unused")
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private val bgLinearTimerShader: Bitmap =
        (context.resources.getDrawable(R.drawable.bg_linear_timer_black_shader) as BitmapDrawable).bitmap;

    private val mPaint: Paint = Paint()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private lateinit var linearGradient: LinearGradient

    private val rect: RectF = RectF()
    private val flashRect: RectF = RectF()
    private var fl = 0F


    init {
        mPaint.isAntiAlias = true
        post {
            val animator = ValueAnimator.ofFloat(width.toFloat(), 0F)
            animator.setDuration(1000).repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.REVERSE
            animator.addUpdateListener {
                fl = it.animatedValue as Float
                invalidate()
            }
            animator.start()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        linearGradient = LinearGradient(
            0F,
            0F,
            measuredWidth * 1.0f,
            measuredHeight * 1.0f,
            Color.parseColor("#ec00eb"),
            Color.parseColor("#ff66d0"),
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rect.set(0F, 0F, width.toFloat(), height.toFloat())

        mPaint.color = Color.TRANSPARENT
        canvas.drawRect(rect, mPaint)
        mPaint.color = Color.WHITE

        val layerId = canvas.saveLayer(0F, 0f, width.toFloat(), height.toFloat(), mPaint)

        rect.left = rect.left - fl
        rect.right = rect.right - fl
        mPaint.xfermode = null
        canvas.drawBitmap(bgLinearTimerShader, null, rect, mPaint)

        mPaint.xfermode = xfermode
        mPaint.shader = linearGradient
        flashRect.set(rect.left, rect.top, rect.right, rect.bottom)
        canvas.drawRect(flashRect, mPaint)

        mPaint.xfermode = null

        canvas.restoreToCount(layerId)
    }
}