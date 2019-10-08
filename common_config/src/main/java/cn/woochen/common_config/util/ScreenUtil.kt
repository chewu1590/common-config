package cn.woochen.common_config.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager


object ScreenUtil {
    fun getScreenRelatedInformation(context: Context) {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        if (windowManager != null) {
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)
            val widthPixels = outMetrics.widthPixels
            val heightPixels = outMetrics.heightPixels
            val densityDpi = outMetrics.densityDpi
            val density = outMetrics.density
            val scaledDensity = outMetrics.scaledDensity
            //可用显示大小的绝对宽度（以像素为单位）。
            //可用显示大小的绝对高度（以像素为单位）。
            //屏幕密度表示为每英寸点数。
            //显示器的逻辑密度。
            //显示屏上显示的字体缩放系数。
            Log.d(
                "display", "widthPixels = " + widthPixels + ",heightPixels = " + heightPixels + "\n" +
                        ",densityDpi = " + densityDpi + "\n" +
                        ",density = " + density + ",scaledDensity = " + scaledDensity
            )
        }
    }

    /**
     * 屏幕宽度
     */
    fun getWidth(context: Context) :Int{
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        if (windowManager != null) {
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)
            val widthPixels = outMetrics.widthPixels
            val heightPixels = outMetrics.heightPixels
           return  widthPixels
        }
        return 0
    }


    /**
     * 屏幕高度
     */
    fun getHeight(context: Context) :Int{
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
        if (windowManager != null) {
            val outMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(outMetrics)
            val widthPixels = outMetrics.widthPixels
            val heightPixels = outMetrics.heightPixels
            return  heightPixels
        }
        return 0
    }

}