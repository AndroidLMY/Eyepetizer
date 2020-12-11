package com.lmy.eyepetizer.utils

import android.util.Log

/**
 * @功能: 全局控制log日志打印配置
 * @Creat 2019/07/15 16:18
 * @User Lmy
 * @By Android Studio
 */
object LogUtils {
    var isPrintLog = true //是否开启打印
    var TGA = "LMY"//log得TAG值

    fun init(tag: String, islog: Boolean) {
        TGA = tag
        isPrintLog = islog
    }
}

