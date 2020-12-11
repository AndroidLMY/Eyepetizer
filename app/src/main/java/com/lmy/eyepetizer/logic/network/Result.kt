package com.lmy.eyepetizer.logic.network

import java.lang.Exception

/**
 * @author lmy
 * @功能: 返回结果的处理封装
 * @Creat 2020/11/6 10:38 AM
 * @Compony 465008238@qq.com
 */
sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T?) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}