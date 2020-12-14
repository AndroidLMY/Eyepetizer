package com.lmy.eyepetizer

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Process
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.jeremyliao.liveeventbus.LiveEventBus
import com.lmy.eyepetizer.utils.LogUtils
import com.lmy.eyepetizer.utils.loge
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.bugly.crashreport.CrashReport.UserStrategy
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.onAdaptListener
import me.jessyan.autosize.unit.Subunits
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*


/**
 * @author
 * @功能:
 * @Creat 12/10/20 4:51 PM
 * @Compony 465008238@qq.com
 */
class EyepetizerApplication : Application() {
    companion object {
        lateinit var mInstance: EyepetizerApplication
        lateinit var context: Context
        var isGrayTheme = false //设置全局灰度
        const val TOKEN = "5u6uTQzJsFwR=xZW"

    }


    init {
        LogUtils.init("LMY", true)
    }


    override fun onCreate() {
        super.onCreate()
        mInstance = this
        context = applicationContext
        initLiveEvent()
        initBugly("注册时申请的APPID")
        initAutoSize(true)
    }


    /**
     * 初始化 LiveEventBus
     */
    private fun initLiveEvent() {
        LiveEventBus.config().apply {
            lifecycleObserverAlwaysActive(true)
            autoClear(false)
        }
    }

    /**
     * 屏幕适配 初始化AutoSize
     * 是否使用主单位 640*360 主单位默认是宽做适配 最大宽度360dp 使用dp作为适配
     * 如果需要对单独界面 限制对高进行适配可以实现CustomAdapt接口 isBaseOnWidth返回false即可
     * 副单位是1920*1080 单位mm
     */
    private fun initAutoSize(isMainUnit: Boolean) {
        if (isMainUnit) {
            AutoSize.initCompatMultiProcess(this)
            /**
             * 以下是 AndroidAutoSize 可以自定义的参数, [AutoSizeConfig] 的每个方法的注释都写的很详细
             * 使用前请一定记得跳进源码，查看方法的注释, 下面的注释只是简单描述!!!
             */
            AutoSizeConfig.getInstance().apply {
                isCustomFragment = true
                onAdaptListener = object : onAdaptListener {
                    override fun onAdaptBefore(target: Any, activity: Activity) {
                        //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会主动重绘当前页面, 所以这时您需要自行重绘当前页面
                        //ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
                        //                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
                        //                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);

                        String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.javaClass.name)
                            .loge("AutoSizeLog")
                    }

                    override fun onAdaptAfter(target: Any, activity: Activity) {
                        String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.javaClass.name)
                            .loge("AutoSizeLog")
                    }
                }
                //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
                //AutoSize 会将屏幕总高度减去状态栏高度来做适配
                //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
                //在全面屏或刘海屏幕设备中, 获取到的屏幕高度可能不包含状态栏高度, 所以在全面屏设备中不需要减去状态栏高度，所以可以 setUseDeviceSize(true)
                isUseDeviceSize = false
                //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
                setLog(true)
                //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                isBaseOnWidth = true
                //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//            setAutoAdaptStrategy { target, activity ->
//            }
            }
        } else {
            //AndroidAutoSize 默认开启对 dp 的支持, 调用 UnitsManager.setSupportDP(false); 可以关闭对 dp 的支持
            //主单位 dp 和 副单位可以同时开启的原因是, 对于旧项目中已经使用了 dp 进行布局的页面的兼容
            //让开发者的旧项目可以渐进式的从 dp 切换到副单位, 即新页面用副单位进行布局, 然后抽时间逐渐的将旧页面的布局单位从 dp 改为副单位
            //最后将 dp 全部改为副单位后, 再使用 UnitsManager.setSupportDP(false); 将 dp 的支持关闭, 彻底隔离修改 density 所造成的不良影响
            //如果项目完全使用副单位, 则可以直接以像素为单位填写 AndroidManifest 中需要填写的设计图尺寸, 不需再把像素转化为 dp

            //AndroidAutoSize 默认开启对 dp 的支持, 调用 UnitsManager.setSupportDP(false); 可以关闭对 dp 的支持
            //主单位 dp 和 副单位可以同时开启的原因是, 对于旧项目中已经使用了 dp 进行布局的页面的兼容
            //让开发者的旧项目可以渐进式的从 dp 切换到副单位, 即新页面用副单位进行布局, 然后抽时间逐渐的将旧页面的布局单位从 dp 改为副单位
            //最后将 dp 全部改为副单位后, 再使用 UnitsManager.setSupportDP(false); 将 dp 的支持关闭, 彻底隔离修改 density 所造成的不良影响
            //如果项目完全使用副单位, 则可以直接以像素为单位填写 AndroidManifest 中需要填写的设计图尺寸, 不需再把像素转化为 dp
            AutoSizeConfig.getInstance().unitsManager.apply {
                isSupportDP = true
                supportSubunits = Subunits.MM
            }
        }

    }

    /**
     * 初始化bugly
     */
    private fun initBugly(appID: String?) {
        val packageName = context.packageName // 获取当前进程名
        val processName = getProcessName(Process.myPid())
        val strategy = UserStrategy(context)
        strategy.isUploadProcess = processName == null || processName == packageName
        CrashReport.initCrashReport(context, appID, false, strategy)
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private fun getProcessName(pid: Int): String? {
        lateinit var reader: BufferedReader
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"))
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim { it <= ' ' }
            }
            return processName
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }
}