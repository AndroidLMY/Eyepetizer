package com.lmy.eyepetizer

import android.app.Application
import android.content.Context
import android.os.Process
import android.text.TextUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lmy.eyepetizer.utils.LogUtils
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException


/**
 * @author
 * @功能:
 * @Creat 12/10/20 4:51 PM
 * @Compony 465008238@qq.com
 */
class EyepetizerApplication : Application() {
    companion object {
        lateinit var context: Context
        var isGrayTheme = false //设置全局灰度
        const val TOKEN = "5u6uTQzJsFwR=xZW"
    }

    init {
        LogUtils.init("LMY", true)
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        LiveEventBus.config().supportBroadcast(this).lifecycleObserverAlwaysActive(true)
            .autoClear(false);
        val packageName = context.packageName // 获取当前进程名
        val processName = getProcessName(Process.myPid())
        val strategy = UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        CrashReport.initCrashReport(context, "注册时申请的APPID", false, strategy)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName: String = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }

}