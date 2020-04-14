package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import kotlin.system.exitProcess

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020-01-20 17:25
 * 修改备注：
 **/
@SuppressLint("StaticFieldLeak")
object ActivityUtil {

    private val activities = mutableListOf<Activity>()

     var topActivity :Activity ? =null

    /**
     * 添加activity
     */
    fun addActivity(activity: Activity){
        if (!activities.contains(activity)){
            activities.add(activity)
        }
    }
    /**
     * 添加activity
     */
    fun removeActivity(activity: Activity){
        if (activities.contains(activity)){
            activities.add(activity)
        }
    }


    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        for (activity in activities) {
            activity.finish()
        }
        activities.clear()
    }


    /**
     * 退出应用程序
     */
    fun appExit(context: Context) {
        try {
            finishAllActivity()
            val manager = context
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            manager.killBackgroundProcesses(context.packageName)
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}