package com.lmy.helloweather.logic.network

import AppConfig.baseUrl
import com.lmy.eyepetizer.EyepetizerApplication
import com.lmy.eyepetizer.logic.network.NetWorkUtils
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * @author Lmy
 * @功能: RetrofitHelp初始化的帮助类
 * @Creat 2020/5/21 10:03
 * @Compony 永远相信美好的事物即将发生
 */
object RetrofitHelp {
    private val loggingInterceptor by lazy {
        HttpLoggingInterceptor()
    }
    private val client by lazy {
        //设置Http缓存
        var cache: Cache? = Cache(
            File(EyepetizerApplication.context.cacheDir, "HttpCache"),
            1024 * 1024 * 10
        )
//        okhttp设置部分，此处还可再设置网络参数
        OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .cache(cache)
            .readTimeout(10, TimeUnit.SECONDS)
            .addNetworkInterceptor(CacheInterceptor())
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    init {
        //初始化loggingInterceptor 日志拦截器  和okhttp3失败重连的配置；
        loggingInterceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * 这里使用泛型来创建ServiceClass
     */
    fun <T> creat(service: Class<T>): T = retrofit.create(service)

    /**
     * 这里使用了泛型实例化 主要的区别在于调用方式
     *  ServiceCreator.creat(PalceService::class.java)
     *  ServiceCreator.creat<PalceService>()
     */
    inline fun <reified T> creat(): T = creat(T::class.java)

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private class CacheInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            // 有网络时 设置缓存超时时间1个小时
            val maxAge = 60 * 60
            // 无网络时，设置超时为1天
            val maxStale = 60 * 60 * 24
            var request = chain.request()
            request = if (NetWorkUtils.isNetworkAvailable(EyepetizerApplication.context)) {
                //有网络时只从网络获取
                request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build()
            } else {
                //无网络时只从缓存中读取
                request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
            var response = chain.proceed(request)
            response = if (NetWorkUtils.isNetworkAvailable(EyepetizerApplication.context)) {
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            } else {
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
            }
            return response
        }
    }
}
