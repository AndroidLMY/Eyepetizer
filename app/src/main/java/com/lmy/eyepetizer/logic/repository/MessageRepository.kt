package com.lmy.eyepetizer.logic.repository

import com.lmy.eyepetizer.logic.bean.MessageBean
import com.lmy.helloweather.base.BaseRepository
import com.lmy.helloweather.logic.network.RetrofitHelp
import com.lmy.helloweather.logic.network.api.TestApi
import java.util.*
import com.lmy.eyepetizer.logic.network.Result

/**
 * @author
 * @功能:
 * @Creat 12/10/20 5:53 PM
 * @Compony 465008238@qq.com
 */
object MessageRepository : BaseRepository() {
    // ?udid=4136b48a6ed35731&vc=6030012&vn=6.3.01&size=1080X2232&deviceModel=16s%20Pro&first_channel=meizu&last_channel=meizu&system_version_code=29
    /**
     *ViewModel层调用获取地址信息数据方法
     */
    suspend fun getMessageList(name: Map<String, String>): Result<List<MessageBean>> {
        return safeApiCall(
            call = { request_getMessageList(name) },
            errorMessage = "获取天气信息失败"
        )
    }

    /**
     * 请求调用地址信息接口数据并返回Result对象
     */
    suspend fun request_getMessageList(name: Map<String, String>): Result<List<MessageBean>> {
        val placeResponse = RetrofitHelp.creat<TestApi>().getTuiJian(name)
        return executeResponse(placeResponse)
    }
}