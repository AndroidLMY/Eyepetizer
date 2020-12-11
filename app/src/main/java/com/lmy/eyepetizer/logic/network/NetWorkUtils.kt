package com.lmy.eyepetizer.logic.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.lmy.eyepetizer.EyepetizerApplication

/**
 * @author
 * @功能:
 * @Creat 12/11/20 9:42 AM
 * @Compony 465008238@qq.com
 */
object NetWorkUtils {

    /**
     * no network
     */
    const val NETWORK_NO = -1

    /**
     * wifi network
     */
    const val NETWORK_WIFI = 1

    /**
     * 数据流量
     */
    private const val NETWORK_MOBILE = 0

    /**
     * "2G" networks
     */
    const val NETWORK_2G = 2

    /**
     * "3G" networks
     */
    const val NETWORK_3G = 3

    /**
     * "4G" networks
     */
    const val NETWORK_4G = 4

    /**
     * unknown network
     */
    const val NETWORK_UNKNOWN = 5

    private const val NETWORK_TYPE_GSM = 16
    private const val NETWORK_TYPE_TD_SCDMA = 17
    private const val NETWORK_TYPE_IWLAN = 18

    /**
     * 判断网络连接是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm == null) {
        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            val info = cm.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 判断网络是否可用
     * 需添加权限
     *
     * @code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
     */
    fun isAvailable(context: Context?): Boolean {
        val info = context?.let { getActiveNetworkInfo(it) }
        return info != null && info.isAvailable
    }

    /**
     * 获取活动网络信息
     *
     * @param context 上下文
     * @return NetworkInfo
     */
    fun getActiveNetworkInfo(context: Context): NetworkInfo? {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }


    /**
     * 判断网络类型
     *
     * @return 0---流量 1---wifi -1---无网络
     */
    open fun getNetWorkState(): Int {
        // 得到连接管理器
        val connectivityManager =
            EyepetizerApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                if (networkInfo.type == ConnectivityManager.TYPE_WIFI) {
                    return NETWORK_WIFI // wifi
                } else if (networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                    return NETWORK_MOBILE // mobile
                }
            } else {
                return NETWORK_NO
            }
        }
        return NETWORK_NO
    }

}