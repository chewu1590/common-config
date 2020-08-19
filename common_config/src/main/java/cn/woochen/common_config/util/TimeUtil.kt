package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间转化工具类
 *
 * @author woochen123
 * @time 2017/3/21 15:35
 */
object TimeUtil {


    /**
     * 时间戳转化为字符串
     *
     * @param time unix时间戳
     * @param pattern 格式 "yyyy-MM-dd HH:mm:ss"
     * @return “2012-2-14 5:12:20”
     */
    @SuppressLint("SimpleDateFormat")
    fun parseTimeStr(time: Long, pattern: String): String {
        if (time == 0L) {
            return "无"
        }
        val sdf = SimpleDateFormat(pattern)
        var date = Date(time)
        if (time.toString().length == 10) {
            date = Date(time * 1000L)
        }
        return sdf.format(date)
    }


    /**
     * 秒转化为形如 36：00的时间
     * time 秒
     */
    fun tranSecToMInSec(time: kotlin.Long): String {
        val sb = StringBuffer()
        val min = time / 60
        val sec = time % 60
        if (min < 10) sb.append("0")
        sb.append(min)
        sb.append(":")
        if (sec < 10) sb.append("0")
        sb.append(sec)
        return sb.toString()

    }

    /**
     * 秒转化为形如 36：00的时间
     * time 秒
     */
    fun tranSecToMInSec2(time: kotlin.Long): String {
        val sb = StringBuffer()
        val min = time / 60
        val sec = time % 60
        if (min > 0) {
            sb.append(min)
            sb.append("分")
        }
        if (sec > 0) {
            sb.append(sec)
            sb.append("秒")
        }
        return sb.toString()

    }

    /**
     * 得到当前时间毫秒值
     */
    fun getCurrentTime(): Long {
        val calendar = Calendar.getInstance()
        return calendar.timeInMillis //这是时间戳
    }


    /**
     * 字符串转时间戳
     *
     * @param timeString
     * @return 10位"1490025600"
     */
    fun getTimeStamp(timeString: String?): Long {
        if (TextUtils.isEmpty(timeString)) return 0
        var timeStamp = 0L
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val d: Date
        try {
            d = sdf.parse(timeString)
            val tempTimeStamp = d.time
            timeStamp = tempTimeStamp
            if (timeStamp.toString().length == 13) {
                //默认转化出来的时间戳是13位，需要变成10位
                timeStamp = (tempTimeStamp / 1000L)
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timeStamp
    }


    /**
     * 更新当前时间 HH:mm:ss
     */
    fun updateCurrentTime(): String {
        val now = Date()
        val format = SimpleDateFormat("HH:mm:ss")
        val time = format.format(now)
        return time
    }

    /**
     * 根据格式返回当前日期
     *
     * @param dateFormatStr "yyyy-MM-dd HH：mm"
     * @return "2012-02-11 12:12"
     */
    private fun getAssignTime(dateFormatStr: String): String {
        val dataFormat = SimpleDateFormat(dateFormatStr)
        val currentTime = System.currentTimeMillis()
        return dataFormat.format(currentTime)
    }


    /**
     * 得到阶段性日期
     *
     * @param monthBefore 几月前
     * @param dayBefore   几天前
     * @return “2011-4-10”
     */
    fun getBeforeDate(monthBefore: Int, dayBefore: Int): String {
        val c = Calendar.getInstance()
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH) - monthBefore, c.get(Calendar.DAY_OF_MONTH) - dayBefore)
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return year.toString() + "-" + (month + 1) + "-" + day
    }

    /**
     * 得到当前日前
     *
     * @return “2011-4-10”
     */
    val currentDate: String
        get() {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            return year.toString() + "-" + (month + 1) + "-" + day
        }


    /**
     * 计算目标时间与当前时间的间隔
     *
     * @param publishTime
     * @return
     * @desc 如：3天前
     */
    fun parseTime(publishTime: Long): String {
        val time: Long
        time = (System.currentTimeMillis() - publishTime) / 1000
        val day: Int
        val hour: Int
        val minute: Int
        val senond: Int
        day = (time / 3600 / 24).toInt()

        if (day == 1) return "1天前"
        if (day > 1) return SimpleDateFormat("yyyy-MM-dd").format(publishTime)
        hour = (time / 3600).toInt()
        if (hour > 0) return hour.toString() + "小时前"
        minute = (time / 60).toInt()
        if (minute > 0) return minute.toString() + "分钟前"
        senond = time.toInt()
        return if (senond >= 0) "刚刚" else ""
    }

    /**
     * 字符串转时间戳
     *
     * @param timeString
     * @return 13位"1490025600000"
     */
    fun getTimeStamp1(timeString: String?): Long {
        if (TextUtils.isEmpty(timeString)) return 0
        var timeStamp = 0L
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val d: Date
        try {
            d = sdf.parse(timeString)
            val tempTimeStamp = d.time
            timeStamp = tempTimeStamp
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return timeStamp
    }


}
