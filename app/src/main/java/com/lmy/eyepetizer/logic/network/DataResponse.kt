package com.lmy.eyepetizer.logic.network

/**
 * @author lmy
 * @功能: 返回结果统一处理的Response类
 * @Creat 2020/11/6 3:56 PM
 * @Compony 465008238@qq.com
 */
class DataResponse<T> {
    var code = 0
    var message: String? = null
    var messageList: T? = null
        private set

    fun setData(data: T) {
        this.messageList = data
    }
}
/**
 * 0: 成功
 * 1000: 非法操作
 * 1001: 参数错误
 * 1002: 请求超时
 * 1003: 参数签名错误
 * 1004: 验证码错误
 * 1005: 文件格式错误
 * 1006: 文件大小超过2m
 * 1007: 上传失败
 */