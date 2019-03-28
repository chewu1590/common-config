package cn.woochen.common_config.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.text.TextUtils
import java.lang.reflect.InvocationTargetException

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
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
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