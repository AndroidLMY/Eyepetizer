package com.lmy.helloweather.base

import com.lmy.eyepetizer.logic.network.DataResponse
import kotlinx.coroutines.coroutineScope
import java.io.IOException
import com.lmy.eyepetizer.logic.network.Result
import com.lmy.eyepetizer.utils.loge

/**
 * @author lmy
 * @功能: Repository层的顶层封装
 * @Creat 2020/11/6 10:37 AM
 * @Compony 465008238@qq.com
 */
open class BaseRepository {
    //retrofit call封装
    suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> {
        return try {
            call()
        } catch (e: Exception) {
            e.message?.loge()
        Result.Error(IOException(errorMessage, e))
        }
    }

    //执行请求函数
    /**
     *  传入一个被DataResponse修饰的T类型  T类型上限是Any  返回一个Result对象
     */
    suspend fun <T : Any> executeResponse(response: DataResponse<T>): Result<T> {
        return coroutineScope {
            //处理code码
            if (response.code != 0) {
                Result.Error(
                    IOException(if (response.message.isNullOrEmpty()) "${response.code}" else response.message)
                )
            } else {
                Result.Success(response.messageList)
            }
        }
    }

}