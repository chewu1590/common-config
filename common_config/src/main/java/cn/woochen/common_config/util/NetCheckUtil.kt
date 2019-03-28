package cn.woochen.common_config.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 网络相关工具类
 * @author woochen
 * @time 2017/10/26 13:43
 * @desc
 */
object NetCheckUtil {

    /**
     * 判断当前网络是否可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivity = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) {
                // 当前网络是连接的
                if (info.state == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true
                }
            }
        }
        return false
    }

}
