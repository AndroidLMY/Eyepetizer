package com.lmy.eyepetizer.ui.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.Observer
import com.gyf.immersionbar.ImmersionBar
import com.lmy.eyepetizer.R
import com.lmy.eyepetizer.databinding.ActivityMainBinding
import com.lmy.eyepetizer.databinding.DefaltDialogBinding
import com.lmy.eyepetizer.logic.viewmodle.MainViewModle
import com.lmy.eyepetizer.receiver.NetWorkChangeBroadcastReceiver
import com.lmy.eyepetizer.ui.dialog.DefaltDialog
import com.lmy.eyepetizer.utils.initViewModel
import com.lmy.eyepetizer.utils.toast
import com.lmy.helloweather.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author lmy
 * @功能: 使用框架之前一定要修改BaseRepository逻辑
 * @Creat 12/11/20 4:14 PM
 * @Compony 465008238@qq.com
 */

class MainActivity : BaseActivity<MainViewModle>(true) {
    override fun layoutId() = R.layout.activity_main
    override fun initVM(): MainViewModle = initViewModel(this)
    override fun initView() {
        ImmersionBar.with(this)
            .reset()
            .statusBarDarkFont(true)   //状态栏字体是深色，不写默认为亮色
            .titleBar(toolbat)
            //解决状态栏和布局重叠问题，任选其一，默认为false，当为true时一定要指定statusBarColor()，不然状态栏为透明色，还有一些重载方法
            .init()


        tv_content.setOnClickListener {
//            ktstartActivity<LoginActivity>()
            val messageTest = "sss"
            DefaltDialog(this).apply {
                setCancelable(false)
                setDefaltBean {
                    defaltBean.apply {
                        message = messageTest
                    }
                }
                setCancelOnclick {
                    dismiss()
                }
                setSureOnclick {
                    dismiss()
                }
            }.show()
        }

    }

    override fun initData() {
        initNetReceiver()
        mViewModel.getMessageList()
        (binding as ActivityMainBinding).apply {
            maindata = mViewModel
        }

    }

    override fun initObserve() {
        mViewModel.apply {
            uiState.observe(this@MainActivity, Observer {
                if (!it.messageList.isNullOrEmpty()) {
                    it.messageList.toString().toast()
                }
            })
        }

    }

    fun initNetReceiver() {
        broadcastReceiver = NetWorkChangeBroadcastReceiver()
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, filter)
    }
}