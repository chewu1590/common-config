package cn.woochen.common_config.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import cn.woochen.common_config.bean.AppInfoBean
import java.lang.reflect.InvocationTargetException

object PackageUtil {

    /**
     * 获取所有安装的其他应用
     */
    fun getAllOtherInstallPackage(context: Context): List<AppInfoBean> {
        val list = mutableListOf<AppInfoBean>()
        // 获取已经安装的所有应用, PackageInfo系统类，包含应用信息
        val packageManager = context.applicationContext.packageManager
        val packages = packageManager.getInstalledPackages(0)
        for (i in packages.indices) {
            val packageInfo = packages[i]
            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) { //非系统应用
                // AppInfo 自定义类，包含应用信息
                if (packageInfo != null) {
                    val appInfo = AppInfoBean(packageInfo.applicationInfo.loadLabel(packageManager).toString(), //获取应用名称
                        packageInfo.packageName, //获取应用包名，可用于卸载和启动应用
                        "0", packageInfo.firstInstallTime, //第一次安装时间
                        packageInfo.lastUpdateTime //最后一次更新时间
                    )
                    list.add(appInfo)
                }
            }
        }
        return list
    }

    /**
     * 获取所有安装的系统应用
     */
    fun getAllSystemInstallPackage(context: Context): List<AppInfoBean> {
        val list = mutableListOf<AppInfoBean>()
        // 获取已经安装的所有应用, PackageInfo系统类，包含应用信息
        val packageManager = context.applicationContext.packageManager
        val packages = packageManager.getInstalledPackages(0)
        for (i in packages.indices) {
            val packageInfo = packages[i]
            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) { //非系统应用
                // AppInfo 自定义类，包含应用信息
                if (packageInfo != null) {
                    val appInfo = AppInfoBean(packageInfo.applicationInfo.loadLabel(packageManager).toString(), //获取应用名称
                        packageInfo.packageName, //获取应用包名，可用于卸载和启动应用
                        "1", packageInfo.firstInstallTime, //第一次安装时间
                        packageInfo.lastUpdateTime //最后一次更新时间
                    )
                    list.add(appInfo)
                }
            }
        }
        return list
    }


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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                code = info.longVersionCode
            }else{
                code = info.versionCode.toLong()
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return code
    }


    /**
     * 获取当前的前台activity
     * @return
     */
    val currentActivity: Activity?
        get() {
            try {
                val activityThreadClass = Class.forName("android.app.ActivityThread")
                val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
                val activitiesField = activityThreadClass.getDeclaredField("mActivities")
                activitiesField.isAccessible = true
                val activities = activitiesField.get(activityThread) as Map<*, *>
                for (activityRecord in activities.values) {
                    val activityRecordClass = activityRecord!!::class.java
                    val pausedField = activityRecordClass.getDeclaredField("paused")
                    pausedField.isAccessible = true
                    if (!pausedField.getBoolean(activityRecord)) {
                        val activityField = activityRecordClass.getDeclaredField("activity")
                        activityField.isAccessible = true
                        return activityField.get(activityRecord) as Activity
                    }
                }
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    /**
     * 获取当前进程名
     */
    private fun getCurrentProcessName(context: Context): String {
        val pid = android.os.Process.myPid()
        var processName = ""
        val manager =
            context.applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in manager.runningAppProcesses) {
            if (process.pid == pid) {
                processName = process.processName
            }
        }
        return processName
    }

    /**
     * 是否为主进程
     */
    fun isMainProcess(context: Context)= context.applicationContext.packageName == getCurrentProcessName(context)


    /**
     *目标apk是否已经安装
     *@author woochen
     *@time 2020/7/17 4:04 PM
     */
    fun isApkInstalled(context: Context, packageName: String?): Boolean {
        if (packageName.isNullOrBlank()) return false
        val packageManager = context.packageManager
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (var4: PackageManager.NameNotFoundException) {
            false
        }
    }
}