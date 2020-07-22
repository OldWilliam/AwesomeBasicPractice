package me.jim.wx.awesomebasicpractice.util

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * Date: 2019-11-27
 * Name: weixin
 * Description: 工具类
 */
fun dp2Px(context: Context, dip: Int): Float {
    return context.resources.displayMetrics.density * dip + 0.5f
}

fun getScreenWidth(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

fun getScreenHeight(context: Context): Int{
    return context.resources.displayMetrics.heightPixels
}

fun setFullscreen(activity: Activity) {
    var uiFlags = 0 // hide status bar
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        uiFlags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE //                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN //                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
    uiFlags = if (Build.VERSION.SDK_INT >= 19) {
        uiFlags or 0x00001000 //SYSTEM_UI_FLAG_IMMERSIVE_STICKY: hide navigation bars - compatibility: building API level is lower thatn 19, use magic number directly for higher API target level
    } else {
        uiFlags or View.SYSTEM_UI_FLAG_LOW_PROFILE
    }
    activity.window.decorView.systemUiVisibility = uiFlags
    activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN) //去掉信息栏
}
