package cn.woochen.common_config.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 *获取常用的包信息
 *@author woochen
 *@time 2019/3/26 2:16 PM
 */
object PackageUtil {

    /**
     * 获取当前的版本名称(verson-name)
     */
    fun getCurrentVersionName(context: Context): String {
        val packageManager = context.packageManager
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(context.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return if (packageInfo != null) packageInfo.versionName else "未知版本"
    }

    /**
     * 获取当前的版本号(verson-code)
     */
    fun getCurrentVersionCode(context: Context): Long {
        val manager = context.packageManager
        var code = 0L
        try {
            val info = manager.getPackageInfo(context.packageName, 0)
            code = info.longVersionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }


}