package me.jim.wx.awesomebasicpractice.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import me.jim.wx.awesomebasicpractice.R

class HearShapeFlash(context: Context, attributes: AttributeSet) : FrameLayout(context, attributes) {
    private val mHeartCover: Bitmap = (context.resources.getDrawable(R.drawable.black_heart) as BitmapDrawable).bitmap;
    private val mWhiteFlash: Bitmap = (context.resources.getDrawable(R.drawable.hani_white_flash) as BitmapDrawable).bitmap;
    private val mPaint: Paint = Paint()
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val clearMode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

    init {
        mPaint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val layerId = canvas.saveLayer(0F, 0f, width.toFloat(), height.toFloat(), mPaint)

        canvas.drawBitmap(mHeartCover, null, RectF(0f,0f, width.toFloat(), height.toFloat()), mPaint)

        mPaint.xfermode = xfermode


        val fl = -30f
        canvas.drawBitmap(mWhiteFlash, null, RectF(0f, fl, width.toFloat(), height.toFloat() + fl), mPaint)

        mPaint.xfermode = clearMode
        canvas.drawRect(RectF(0f, height.toFloat() - fl, width.toFloat(), height.toFloat()), mPaint)

        canvas.restoreToCount(layerId)
    }

}