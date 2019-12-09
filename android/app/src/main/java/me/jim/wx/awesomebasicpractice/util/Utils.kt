package me.jim.wx.awesomebasicpractice.util

import android.content.Context

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
