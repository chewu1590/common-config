package cn.woochen.common_config.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 *默认的Retrofit管理类(可作为参考用于自定义)
 *@author woochen
 *@time 2019/3/26 10:32 AM
 */
class DefaultRetrofitUtil {
    private constructor()

    private var debugMode = true
    private var timeout = 5L
    private val intercepters = mutableListOf<Interceptor>()
    var retrofit: Retrofit? = null
        get() {
            if (field == null) {
                throw IllegalArgumentException("please init Retrofit first!")

            }
            return field
        }

    companion object {
        private var mInstance: DefaultRetrofitUtil? = null

        @Synchronized
        fun getInstance(): DefaultRetrofitUtil {
            if (mInstance == null) mInstance = DefaultRetrofitUtil()
            return mInstance as DefaultRetrofitUtil
        }

    }

    fun init(baseUrl: String) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (debugMode) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        if (intercepters.isNotEmpty()) {
            for (intercepter in intercepters) {
                clientBuilder.addInterceptor(intercepter)
            }
        }
        retrofit = Retrofit.Builder()
            .client(clientBuilder.build())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    /**
     * 日志开关（默认debug打印日志，release关闭日志）
     */
    fun debugMode(isDebug: Boolean): DefaultRetrofitUtil {
        debugMode = isDebug
        return this
    }

    /**
     * 超时时间(秒)(包含connectTimeout，writeTimeout，readTimeout)
     */
    fun timeout(timeoutSec: Long): DefaultRetrofitUtil {
        timeout = timeoutSec
        return this
    }

    /**
     * 拦截器集合
     */
    fun interceptors(intercepterList: List<Interceptor>): DefaultRetrofitUtil {
        intercepters.addAll(intercepterList)
        return this
    }

    fun interceptor(intercepterList: Interceptor): DefaultRetrofitUtil {
        intercepters.add(intercepterList)
        return this
    }

}
