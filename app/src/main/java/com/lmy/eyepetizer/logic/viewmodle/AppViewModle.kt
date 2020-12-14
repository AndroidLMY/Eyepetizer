package com.lmy.eyepetizer.logic.viewmodle

import android.app.Application
import androidx.lifecycle.*
import com.lmy.eyepetizer.EyepetizerApplication
import com.lmy.eyepetizer.logic.bean.TestEvent

/**
 * @author
 * @功能:
 * @Creat 12/10/20 5:57 PM
 * @Compony 465008238@qq.com
 */
class LiveDataBusPlus<T> {
    private val _liveDataBus = MutableLiveData<T>()
    val liveDataBus: LiveData<T> = _liveDataBus
    fun postValue(value: T) {
        _liveDataBus.postValue(value!!)
    }

    companion object {
        private lateinit var appViewModel: LiveDataBusPlus<*>
        fun getAppViewModle(): LiveDataBusPlus<*> {
            if (!::appViewModel.isInitialized) {
                appViewModel = LiveDataBusPlus<Any>()
            }
            return appViewModel
        }
    }
}

fun <T> LiveDataBus(): LiveDataBusPlus<T> {
    return LiveDataBusPlus.getAppViewModle() as LiveDataBusPlus<T>
}







