package com.lmy.eyepetizer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.lmy.eyepetizer.logic.network.NetWorkUtils.getNetWorkState
import com.lmy.eyepetizer.utils.toast

/**
 * Created by damon on 2018/6/11.
 */
class NetWorkChangeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action!!
        if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
            val state = getNetWorkState() // 判断网络是什么类型，0为流量1为wifi
            if (state == 0) {
                "当前连接为数据流量".toast()
            } else if (state == 1) {
                "当前连接为wifi".toast()
            } else {
                "当前没有网络连接".toast()
            }
        }
    }
}