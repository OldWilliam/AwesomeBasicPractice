package me.jim.wx.awesomebasicpractice.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import me.jim.wx.awesomebasicpractice.R
import kotlin.math.max
import kotlin.math.min

class HeartEdgeFlash(context: Context, attributes: AttributeSet) : FrameLayout(context, attributes) {
    private val mHeartCover: Bitmap = (context.resources.getDrawable(R.drawable.black_heart) as BitmapDrawable).bitmap;
    private val mWhiteFlash: Bitmap = (context.resources.getDrawable(R.drawable.white_flash) as BitmapDrawable).bitmap;
    private val mPaint: Paint = Paint()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val clearMode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    private val rect: RectF = RectF()
    private val flashRect: RectF = RectF()

    private var fl = 0f

    init {
        mPaint.isAntiAlias = true
        post {

            val animator = ValueAnimator.ofFloat(height.toFloat(), -height.toFloat())
            animator.setDuration(1000).repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.REVERSE
            animator.addUpdateListener {
                fl = it.animatedValue as Float
                invalidate()
            }
            animator.start()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        rect.set(0F, 0F, width.toFloat(), height.toFloat())

        mPaint.xfermode = null
        mPaint.color = Color.TRANSPARENT
        canvas.drawRect(rect, mPaint)
        mPaint.color = Color.WHITE

        val layerId = canvas.saveLayer(0F, 0f, width.toFloat(), height.toFloat(), mPaint)

        mPaint.xfermode = null
        canvas.drawBitmap(mHeartCover, null, rect, mPaint)

        mPaint.xfermode = xfermode
        flashRect.set(rect.left, rect.top + fl, rect.right, rect.bottom + fl)
        canvas.drawBitmap(mWhiteFlash, null, flashRect, mPaint)

        mPaint.xfermode = clearMode
        canvas.drawRect(0F, 0F, width.toFloat(), max(0F, fl) + 1, mPaint)
        canvas.drawRect(0F, min(height.toFloat() + fl, height.toFloat()) - 1, width.toFloat(), height.toFloat(), mPaint)

        mPaint.xfermode = null

        canvas.restoreToCount(layerId)
    }

}