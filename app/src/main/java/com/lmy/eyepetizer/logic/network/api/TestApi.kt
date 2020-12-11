package com.lmy.helloweather.logic.network.api

import com.lmy.eyepetizer.logic.bean.MessageBean
import com.lmy.eyepetizer.logic.network.DataResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 * @author Lmy
 * @功能:
 * @Creat 2020/5/21 14:28
 * @Compony 永远相信美好的事物即将发生
 */
interface TestApi {
    @GET("v3/messages")
    suspend fun getTuiJian(@QueryMap map: Map<String, String>): DataResponse<List<MessageBean>>
}