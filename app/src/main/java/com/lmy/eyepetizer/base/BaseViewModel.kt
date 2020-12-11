package com.lmy.helloweather.base

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.lmy.eyepetizer.logic.network.Result
import com.lmy.eyepetizer.utils.loge

/**
 * @author lmy
 * @功能: ViewModel层的顶层封装
 * @Creat 2020/11/6 10:37 AM
 * @Compony 465008238@qq.com
 */
open class BaseViewModel : ViewModel(), LifecycleObserver, CoroutineScope {
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main

    /**
     * 检查返回的Result成功或者失败
     */
    open fun <T : Any> checkResult(
        result: Result<T>, success: (T?) -> Unit, error: (String?) -> Unit
    ) {
        if (result is Result.Success) {
            success(result.data)
        } else if (result is Result.Error) {
            error(result.exception.message)
        }
    }

}



