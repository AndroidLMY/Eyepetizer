package com.lmy.helloweather.base

import android.app.Activity
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.lmy.eyepetizer.EyepetizerApplication
import com.lmy.eyepetizer.receiver.NetWorkChangeBroadcastReceiver
import com.lmy.eyepetizer.ui.dialog.LoadingDialog

/**
 * @功能:
 * @Creat 2020/6/5 13:27
 * @User Lmy
 * @Compony JinAnChang
 */
abstract class BaseActivity<VM : BaseViewModel>(isDataBinding: Boolean = true) :
    AppCompatActivity() {
    //是否要使用DataBinding
    protected open val isBinding = isDataBinding
    protected var mContext: Activity? = null
    protected lateinit var mViewModel: VM
    protected lateinit var binding: ViewDataBinding
    protected var isFirstRequest: Boolean = true
    var broadcastReceiver: NetWorkChangeBroadcastReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        mViewModel = initVM()
        lifecycle.addObserver(mViewModel!!)
        mContext = this
        initStyle()
        initView()
        initObserve()
        if (isFirstRequest) {
            initData()
        }
    }


    private fun initDataBinding() {
        if (isBinding) {
            binding = DataBindingUtil.setContentView(this, layoutId())
            binding.lifecycleOwner = this

        } else {
            setContentView(layoutId())
        }
    }

    /**
     * 初始化Activity的界面风格
     */
    private fun initStyle() {

        ImmersionBar.with(this)
            .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
            .fitsSystemWindows(true)    //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
            .init()

    }

    /**
     * 重写onCreateView
     * 用于设置是否要增加全局灰度的主题
     */
    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        //添加灰度主题
        if (EyepetizerApplication.isGrayTheme)
            setGaryTheme(window)
        return super.onCreateView(name, context, attrs)
    }

    /**
     * 设置全局灰度主题
     */
    fun setGaryTheme(window: Window) {
        val paint = Paint()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        paint.colorFilter = ColorMatrixColorFilter(cm)
        window.decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint)
    }

    /**
     * 布局文件id
     */
    abstract fun layoutId(): Int

    /**
     *初始化ViewModle
     */
    abstract fun initVM(): VM


    /**
     *初始化view
     */
    abstract fun initView()

    /**
     * 初始化订阅监听
     */
    abstract fun initObserve()

    /**
     * 初始化数据
     */
    open fun initData() {}

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.let {
            lifecycle.removeObserver(it)
        }
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
        }
        mContext = null
    }

    private var loadingDialog: LoadingDialog? = null
    open fun showProgress() {
        mContext?.let {
            if (loadingDialog == null) {
                loadingDialog = LoadingDialog(mContext!!).apply {
                    setCancelable(false)
                }
            }
            loadingDialog?.apply {
                show()
                showLoading()
            }
        }
    }

    open fun hideProgress() {
        mContext?.let {
            loadingDialog?.apply {
                cancel()
                hisLoading()
            }
        }
    }

}