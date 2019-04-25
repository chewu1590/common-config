package cn.woochen.common_config.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * 网络相关工具类
 * @author woochen
 * @time 2017/10/26 13:43
 * @desc
 */
object NetCheckUtil {

    /**
     * 判断网络是否可用
     */
    fun isAvailable(context: Context): Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return !(networkCapabilities == null || !networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
        } else {
            //api23以下仍然使用networkInfo进行判断
            // !!!连接不可用wifi的情况下，这里仍然会返回true
            //可以采用ping的方式来实现，但是耗时过长
            val info = connectivityManager.activeNetworkInfo
            if (info != null) return info.isAvailable
        }
        return false
    }


    /**
     * 是否是wifi网络
     * @return true wifi环境（包含不可用wifi),可配合[isAvailable]进行判断
     */
    fun isWifiNet(context: Context): Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities != null) {
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        } else {
            val info = connectivityManager.activeNetworkInfo
            return (info != null && ConnectivityManager.TYPE_WIFI == info.type)
        }
        return false
    }




}
