package cn.woochen.common_config.net

import okhttp3.EventListener
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class DefaultRetrofitUtil {

    private constructor()

    private val converterFactories = mutableListOf<Converter.Factory>()
    private val callAdapterFactories = mutableListOf<CallAdapter.Factory>()
    private var eventListener: EventListener.Factory? = null

    private var debugMode = true
    private var canProxy = true
    private var timeout = 5L
    private val intercepters = mutableListOf<Interceptor>()
    private val networkIntercepters = mutableListOf<Interceptor>()
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

    private  var clientBuilder: OkHttpClient.Builder? =null

    private  var retrofitBuilder: Retrofit.Builder?=null

    fun init(baseUrl: String) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (debugMode) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        if (clientBuilder == null) {
            clientBuilder = OkHttpClient.Builder()
            if (!canProxy) clientBuilder?.proxy(Proxy.NO_PROXY)
            eventListener?.let {
                clientBuilder?.eventListenerFactory(it)
            }
            clientBuilder?.connectTimeout(timeout, TimeUnit.SECONDS)?.writeTimeout(timeout, TimeUnit.SECONDS)
                ?.readTimeout(timeout, TimeUnit.SECONDS)?.addInterceptor(httpLoggingInterceptor)


            if (intercepters.isNotEmpty()) {
                for (intercepter in intercepters) {
                    clientBuilder?.addInterceptor(intercepter)
                }
            }
            if (networkIntercepters.isNotEmpty()) {
                for (intercepter in networkIntercepters) {
                    clientBuilder?.addNetworkInterceptor(intercepter)
                }
            }
        }
        if (retrofitBuilder == null) {
            retrofitBuilder = Retrofit.Builder()
            retrofitBuilder?.client(clientBuilder?.build())?.baseUrl(baseUrl)
            if (converterFactories.size > 0) {
                converterFactories.forEach {
                    retrofitBuilder?.addConverterFactory(it)
                }
            } else {
                retrofitBuilder?.addConverterFactory(GsonConverterFactory.create())
            }
            if (callAdapterFactories.size > 0) {
                callAdapterFactories.forEach {
                    retrofitBuilder?.addCallAdapterFactory(it)
                }
            } else {
                retrofitBuilder?.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            }
        }
        retrofit = retrofitBuilder?.build()
    }

    /**
     * 日志开关（默认debug打印日志，release关闭日志）
     */
    fun debugMode(isDebug: Boolean): DefaultRetrofitUtil {
        debugMode = isDebug
        return this
    }

    /**
     * 代理开关（默认允许抓包）
     */
    fun proxy(canProxy: Boolean): DefaultRetrofitUtil {
        this.canProxy = canProxy
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
     * 拦截器集合(多个)
     */
    fun interceptors(intercepterList: List<Interceptor>): DefaultRetrofitUtil {
        intercepters.addAll(intercepterList)
        return this
    }

    /**
     * 拦截器集合(单个)
     */
    fun interceptor(intercepter: Interceptor): DefaultRetrofitUtil {
        intercepters.add(intercepter)
        return this
    }

    /**
     * 网络拦截器集合(多个)
     */
    fun networkInterceptors(intercepterList: List<Interceptor>): DefaultRetrofitUtil {
        networkIntercepters.addAll(intercepterList)
        return this
    }

    /**
     * 网络拦截器集合(单个)
     */
    fun networkInterceptor(intercepter: Interceptor): DefaultRetrofitUtil {
        networkIntercepters.add(intercepter)
        return this
    }


    /**
     * 转化器
     */
    fun converterFactory(converterFactory: Converter.Factory): DefaultRetrofitUtil {
        converterFactories.add(converterFactory)
        return this
    }


    /**
     * 适配器工厂
     */
    fun callAdapterFactory(callAdapterFactory: CallAdapter.Factory): DefaultRetrofitUtil {
        callAdapterFactories.add(callAdapterFactory)
        return this
    }

    /**
     * 适配器工厂
     */
    fun eventListenerFactory(eventListener: EventListener.Factory): DefaultRetrofitUtil {
        this.eventListener = eventListener
        return this
    }

    /**
     * 自定义okhttp引擎
     */
    fun okhttpClientBuilder(builder: OkHttpClient.Builder): DefaultRetrofitUtil {
        this.clientBuilder = builder
        return this
    }

    /**
     * 自定义retrofit引擎
     */
    fun retrofitBuilder(builder: Retrofit.Builder): DefaultRetrofitUtil {
        this.retrofitBuilder = builder
        return this
    }


}