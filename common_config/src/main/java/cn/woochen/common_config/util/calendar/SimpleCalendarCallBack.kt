package cn.woochen.common_config.util.calendar

import android.database.Cursor
import cn.woochen.common_config.util.calendar.CalendarCallBack

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/11/30 3:43 PM
 * 修改备注：
 **/
open class SimpleCalendarCallBack : CalendarCallBack {
    override fun queryResult(cursor: Cursor?) {

    }

    override fun insertResult(result: Long) {

    }

    override fun deleteResult(result: Int) {

    }

    override fun remindResult(result: Boolean) {

    }

}