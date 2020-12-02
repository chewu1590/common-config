package cn.woochen.common_config.util.calendar

import android.database.Cursor

/**
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/11/27 7:21 PM
 * 修改备注：
 */
interface CalendarCallBack {
    fun queryResult(cursor: Cursor?)
    fun insertResult(result: Long)
    fun deleteResult(result: Int)
    fun remindResult(result: Boolean)
}


