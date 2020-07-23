package me.jim.wx.awesomebasicpractice.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.game_touch_pad.view.*
import me.jim.wx.awesomebasicpractice.R
import me.jim.wx.awesomebasicpractice.util.dp2Px
import kotlin.math.sqrt

class GameTouchPad : FrameLayout {


    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.game_touch_pad, this)
        val dp2Px = dp2Px(context, 20).toInt()
        setPadding(dp2Px, dp2Px, dp2Px, dp2Px)
        clipToPadding = false
        clipChildren = false
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        maxRange = touch_area.measuredWidth / 2.0F

        centerX = touch_area.measuredWidth / 2
        centerY = touch_area.measuredHeight / 2
    }

    private var maxRange: Float = 0F

    private var centerX: Int = 0
    private var centerY: Int = 0


    private var lastX: Float = 0F
    private var lastY: Float = 0F


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        requestDisallowInterceptTouchEvent(true)
        return super.dispatchTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x
                lastY = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                moveBall(event.x - lastX, event.y - lastY)
                lastX = event.x
                lastY = event.y
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                resetBall()
            }
        }
        return true
    }

    private fun resetBall() {

        resetToCenter(touch_ball)
        resetToCenter(ball_img)

        top_sector.visibility = View.INVISIBLE
        top_arrow.visibility = View.VISIBLE
        top_light_arrow.visibility = View.INVISIBLE

        bottom_sector.visibility = View.INVISIBLE
        bottom_arrow.visibility = View.VISIBLE
        bottom_light_arrow.visibility = View.INVISIBLE
    }

    private fun resetToCenter(view: View) {
        val parent = view.parent as ViewGroup
        view.offsetLeftAndRight((parent.width - view.width) / 2 - view.left)
        view.offsetTopAndBottom((parent.height - view.height) / 2 - view.top)
    }

    private var mListener: TouchPadListener? = null

    @Suppress("unused", "RedundantVisibilityModifier")
    public fun setOnTouchListener(listener: TouchPadListener) {
        mListener = listener
    }

    @SuppressLint("SetTextI18n")
    private fun moveBall(dx: Float, dy: Float) {

        val ballX = touch_ball.left + touch_ball.width / 2
        val bally = touch_ball.top + touch_ball.height / 2

        //计算两点之间距离，勾股定理
        val finalX = ballX + dx
        val finalY = bally + dy

        val sideA = finalX - centerX
        val sideB = finalY - centerY
        val sideC = sqrt(sideA * sideA + sideB * sideB)

        if (sideC <= maxRange) {
            touch_ball.offsetTopAndBottom(dy.toInt())
            touch_ball.offsetLeftAndRight(dx.toInt())
            ball_img.offsetTopAndBottom(dy.toInt())
            ball_img.offsetLeftAndRight(dx.toInt())

            val scaleX = (centerX - finalX) / maxRange
            val scaleY = (centerY - finalY) / maxRange

            touch_ball.text = "(${format(scaleX)}, ${format(scaleY)})"

            mListener?.onMove(format(scaleX), format(scaleY))
        }

        val limit = 10
        if (touch_ball.top - limit < 0) {
            top_sector.visibility = View.VISIBLE
            top_light_arrow.visibility = View.VISIBLE
            top_arrow.visibility = View.INVISIBLE
        } else {
            top_sector.visibility = View.INVISIBLE
            top_light_arrow.visibility = View.INVISIBLE
            top_arrow.visibility = View.VISIBLE
        }

        if (touch_ball.bottom + limit > touch_area.height) {
            bottom_sector.visibility = View.VISIBLE
            bottom_light_arrow.visibility = View.VISIBLE
            bottom_arrow.visibility = View.INVISIBLE
        } else {
            bottom_sector.visibility = View.INVISIBLE
            bottom_light_arrow.visibility = View.INVISIBLE
            bottom_arrow.visibility = View.VISIBLE
        }
    }

    private fun format(value: Float) = String.format("%.2f", value).toFloat()

    public fun setLineTips(text: CharSequence) {
        line_tips.text = text
    }

    interface TouchPadListener {
        fun onMove(x: Float, y: Float)
    }
}