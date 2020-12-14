package com.lmy.eyepetizer.ui.activity

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.Observer
import com.lmy.eyepetizer.R
import com.lmy.eyepetizer.logic.bean.TestEvent
import com.lmy.eyepetizer.logic.viewmodle.LiveDataBus
import com.lmy.eyepetizer.logic.viewmodle.MainViewModle
import com.lmy.eyepetizer.receiver.NetWorkChangeBroadcastReceiver
import com.lmy.eyepetizer.utils.initViewModel
import com.lmy.eyepetizer.utils.ktstartActivity
import com.lmy.eyepetizer.utils.loge
import com.lmy.eyepetizer.utils.toast
import com.lmy.helloweather.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import me.jessyan.autosize.internal.CustomAdapt


/**
 * @author lmy
 * @功能: 使用框架之前一定要修改BaseRepository逻辑
 * @Creat 12/11/20 4:14 PM
 * @Compony 465008238@qq.com
 */

class MainActivity : BaseActivity<MainViewModle>(false), CustomAdapt {
    override fun layoutId() = R.layout.activity_main
    override fun initVM(): MainViewModle = initViewModel(this)
    override fun initView() {
    }

    override fun initData() {
        initNetReceiver()
        mViewModel.getMessageList()
        tv_cesgu.setOnClickListener {
            ktstartActivity<LoginActivity>()
        }
        tv_cesgu2.setOnClickListener {
            LiveDataBus<TestEvent>().postValue(
                TestEvent("shabiLoginActivity")
            )
        }
        LiveDataBus<TestEvent>().liveDataBus.observe(
            this, Observer {
                "shabiMainActivity".loge()
            })

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

    override fun isBaseOnWidth() = true
    override fun getSizeInDp() = 0f
}