package cn.woochen.common_config.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import cn.woochen.common_config.bean.AppInfo
import java.lang.reflect.InvocationTargetException

object PackageUtil {

    /**
     * 获取所有安装的其他应用
     */
    fun getAllOtherInstallPackages(context: Context): List<AppInfo> {
        val list = mutableListOf<AppInfo>()
        // 获取已经安装的所有应用, PackageInfo系统类，包含应用信息
        val packageManager = context.applicationContext.packageManager
        val packages = packageManager.getInstalledPackages(0)
        for (i in packages.indices) {
            val packageInfo = packages[i]
            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM == 0) { //非系统应用
                // AppInfo 自定义类，包含应用信息
                if (packageInfo != null){
                    val appInfo = AppInfo(
                        packageInfo.applicationInfo.loadLabel(packageManager).toString(), //获取应用名称
                        packageInfo.packageName, //获取应用包名，可用于卸载和启动应用
                        packageInfo.versionName, //获取应用版本名称
                        packageInfo.versionCode,//获取应用版本号
                        packageInfo.firstInstallTime,//第一次安装时间
                        packageInfo.lastUpdateTime//最后一次更新时间
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
    fun getAllSystemInstallPackages(context: Context): List<AppInfo> {
        val list = mutableListOf<AppInfo>()
        // 获取已经安装的所有应用, PackageInfo系统类，包含应用信息
        val packageManager = context.applicationContext.packageManager
        val packages = packageManager.getInstalledPackages(0)
        for (i in packages.indices) {
            val packageInfo = packages[i]

            if (packageInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) { //非系统应用
                // AppInfo 自定义类，包含应用信息
                if (packageInfo !=null){
                    val appInfo = AppInfo(
                        packageInfo.applicationInfo.loadLabel(packageManager).toString(), //获取应用名称
                        packageInfo.packageName, //获取应用包名，可用于卸载和启动应用
                        packageInfo.versionName, //获取应用版本名称
                        packageInfo.versionCode,//获取应用版本号
                        packageInfo.firstInstallTime,//第一次安装时间
                        packageInfo.lastUpdateTime//最后一次更新时间
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
     * @param context
     * @return 进程名
     */
    fun getProcessName(context: Context): String {
        var processName: String? = null

        // ActivityManager
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        while (true) {
            for (info in am.runningAppProcesses) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName
                    break
                }
            }
            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName!!
            }
            // take a rest and again
            try {
                Thread.sleep(100L)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }

        }
    }
}