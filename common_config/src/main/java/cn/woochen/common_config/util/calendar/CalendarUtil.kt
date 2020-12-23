package cn.woochen.common_config.util.calendar

import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import android.util.SparseArray
import java.util.*
import kotlin.concurrent.thread

/**
 *日历类
 *@author woochen
 *@time 2020/11/27 5:50 PM
 */
object CalendarUtil {

    private var context: Context? = null

    private val mContentResolver by lazy {
        context?.run { contentResolver } ?: throw IllegalArgumentException("please invoke CalendarUtil init() first")
    }

    //日历应用的content provider对应的uri(日历表的路径)
    private val mCalendarUri by lazy { CalendarContract.Calendars.CONTENT_URI }

    //事件表的路径
    private val mEventUri by lazy { CalendarContract.Events.CONTENT_URI }

    //提醒表的路径
    private val mReminderUri by lazy { CalendarContract.Reminders.CONTENT_URI }

    //需要查询的字段
    private val CAL_PROJECTION by lazy {
        arrayOf(CalendarContract.Calendars._ID, CalendarContract.Calendars.NAME, CalendarContract.Calendars.OWNER_ACCOUNT,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CalendarContract.Calendars.ACCOUNT_NAME,
            CalendarContract.Calendars.ACCOUNT_TYPE)
    }

    //[queryId,QueryResult]
    private val querySparse by lazy { SparseArray<CalendarCallBack?>() }

    //异步查询请求对应的索引值
    private var queryId = 1


    /**
     * 初始化，只用调用一次
     * @param context Context
     */
    fun init(context: Context) {
        CalendarUtil.context = context.applicationContext
    }

    /**
     * 查询所有日历(同步)
     * @param calendarCallBack 查询结果回调
     * @param 是否异步 查询结果回调，默认为同步
     * @see Cursor
     */
    fun queryCalendar(calendarCallBack: CalendarCallBack?, isAsync: Boolean = false) {
        if (isAsync) {
            asyncOperate(calendarCallBack) {
                mAsyncQueryHandler.startQuery(queryId, null, mCalendarUri, CAL_PROJECTION, null, null, null)
            }
        } else {
            var cursor: Cursor?
            try {
                cursor = mContentResolver.query(mCalendarUri, CAL_PROJECTION, null, null, null)
                cursor?.count
            } catch (e: Exception) {
                cursor = null
            }
            calendarCallBack?.queryResult(cursor)
        }
    }

    /**
     * 异步操作
     * @param callBack CalendarCallBack?
     * @param block Function0<Unit>
     */
    private fun asyncOperate(callBack: CalendarCallBack?, block: () -> Unit) {
        querySparse.put(queryId, callBack)
        block.invoke()
        queryId++
    }

    /**
     * 异步操作结果
     * @param token Int
     * @param block Function0<Unit>
     */
    private fun asyncResult(token: Int, block: () -> Unit) {
        if (querySparse.indexOfKey(token) >= 0) {
            block.invoke()
            querySparse.remove(token)
        }
    }

    /**
     * 添加日历账户(同步)
     * @param calendarName 日历名称
     * @param accountName 同步适配器操作对应的日历账户名称
     * @param accountType 同步适配器对应的日历账户类型
     * @return 账户创建成功则返回账户id，否则返回-1
     */
    fun insertCalendar(calendarName: String, accountName: String, accountType: String): Long {
        val timeZone = TimeZone.getDefault()
        val value = ContentValues().apply {
            put(CalendarContract.Calendars.NAME, calendarName) //日历名称
            put(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
            put(CalendarContract.Calendars.ACCOUNT_TYPE, accountType)
            put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, accountName)
            put(CalendarContract.Calendars.VISIBLE, 1)
            put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
            put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER)
            put(CalendarContract.Calendars.SYNC_EVENTS, 1)
            put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
            put(CalendarContract.Calendars.OWNER_ACCOUNT, accountName)
            put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        }
        var calendarUri = mCalendarUri.buildUpon().appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, accountType).build()
        val result = mContentResolver.insert(calendarUri, value)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 添加日历账户(异步)
     * @param calendarName 日历名称
     * @param accountName 同步适配器操作对应的日历账户名称
     * @param accountType 同步适配器对应的日历账户类型
     * @return 账户创建成功则返回账户id，否则返回-1
     */
    fun insertCalendarSync(calendarName: String, accountName: String, accountType: String, calendarCallBack: CalendarCallBack? = null) {
        val timeZone = TimeZone.getDefault()
        val value = ContentValues().apply {
            put(CalendarContract.Calendars.NAME, calendarName) //日历名称
            put(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
            put(CalendarContract.Calendars.ACCOUNT_TYPE, accountType)
            put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, accountName)
            put(CalendarContract.Calendars.VISIBLE, 1)
            put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
            put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER)
            put(CalendarContract.Calendars.SYNC_EVENTS, 1)
            put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
            put(CalendarContract.Calendars.OWNER_ACCOUNT, accountName)
            put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        }
        var calendarUri = mCalendarUri.buildUpon().appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, accountType).build()
        asyncOperate(calendarCallBack) {
            mAsyncQueryHandler.startInsert(queryId, null, calendarUri, value)
        }
    }

    /**
     * 通过id删除日历(同步)
     * @param calendarId 日历id
     * @return 删除的记录数量
     */
    fun deleteCalendar(calendarId: Long): Int {
        val updateUri: Uri = ContentUris.withAppendedId(mCalendarUri, calendarId)
        return mContentResolver.delete(updateUri, null, null)
    }

    /**
     * 通过id删除日历(异步)
     * @param calendarId 日历id
     * @param calendarCallBack CalendarCallBack?
     */
    fun deleteCalendarAsync(calendarId: Long, calendarCallBack: CalendarCallBack? = null) {
        val updateUri: Uri = ContentUris.withAppendedId(mCalendarUri, calendarId)
        asyncOperate(calendarCallBack) {
            mAsyncQueryHandler.startDelete(queryId, null, updateUri, null, null)
        }
    }

    /**
     * 添加日历事件(同步)
     * @param calendarId 日历id
     * @param startMillis 事件开始时间
     * @param endMillis 事件结束时间
     * @param eventTitle 事件标题
     * @param eventDesc 事件描述
     * @return 事件插入成功则返回事件id，否则返回-1
     */
    fun insertEvent(calendarId: Long, startMillis: Long, endMillis: Long, eventTitle: String, eventDesc: String, rRULE: String): Long {
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, eventTitle)
            put(CalendarContract.Events.DESCRIPTION, eventDesc)
            put(CalendarContract.Events.CALENDAR_ID, calendarId)
            put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai")
            put(CalendarContract.Events.RRULE, rRULE)
        }
        val result = mContentResolver.insert(mEventUri, values)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 添加日历事件(异步)
     * @param calendarId 日历id
     * @param startMillis 事件开始时间
     * @param endMillis 事件结束时间
     * @param eventTitle 事件标题
     * @param eventDesc 事件描述
     * @param rRULE 事件重复规则，如："FREQ=DAILY;COUNT=14;BYHOUR=9,15;BYMINUTE=00"表示每天重复，一共执行14次，在9：00和15：00点执行
     */
    fun insertEventAsync(calendarId: Long, startMillis: Long, endMillis: Long, eventTitle: String, eventDesc: String, rRULE: String, calendarCallBack: CalendarCallBack? = null) {
        val value = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, startMillis)
            put(CalendarContract.Events.DTEND, endMillis)
            put(CalendarContract.Events.TITLE, eventTitle)
            put(CalendarContract.Events.DESCRIPTION, eventDesc)
            put(CalendarContract.Events.CALENDAR_ID, calendarId)
            put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai")
            put(CalendarContract.Events.RRULE, rRULE)
        }
        asyncOperate(calendarCallBack) {
            mAsyncQueryHandler.startInsert(queryId, null, mEventUri, value)
        }
    }

    /**
     * 删除日历事件(同步)
     * @param calId 事件id
     * @return 删除的记录数量
     */
    fun deleteEvent(calId: Long): Int {
        val updateUri: Uri = ContentUris.withAppendedId(mEventUri, calId)
        return mContentResolver.delete(updateUri, null, null)
    }

    /**
     * 删除日历事件(异步)
     * @param calId Long
     * @param calendarCallBack CalendarCallBack?
     */
    fun deleteEventAsync(calId: Long, calendarCallBack: CalendarCallBack? = null) {
        val updateUri: Uri = ContentUris.withAppendedId(mEventUri, calId)
        asyncOperate(calendarCallBack) {
            mAsyncQueryHandler.startDelete(queryId, null, updateUri, null, null)
        }
    }

    /**
     * 查询日历事件
     * @param calendarCallBack CalendarCallBack?
     * @param isAsync Boolean
     */
    fun queryEvents(calendarCallBack: CalendarCallBack?, isAsync: Boolean = false) {
        if (isAsync) {
            asyncOperate(calendarCallBack) {
                mAsyncQueryHandler.startQuery(queryId, null, mEventUri, null, null, null, null)
            }
        } else {
            var cursor: Cursor?
            try {
                cursor = mContentResolver.query(mEventUri, null, null, null, null)
                cursor?.count
            } catch (e: Exception) {
                cursor = null
            }
            calendarCallBack?.queryResult(cursor)
        }
    }


    /**
     * 插入事件提醒(同步)
     * @param eventId 事件id
     * @return 提醒创建成功则返回提醒id，否则返回-1
     */
    fun insertReminders(eventId: Long): Long {
        val values = ContentValues().apply {
            put(CalendarContract.Reminders.EVENT_ID, eventId)
            put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        }
        val result = mContentResolver.insert(mReminderUri, values)
        return if (result == null) -1 else ContentUris.parseId(result)
    }

    /**
     * 插入事件提醒(异步)
     * @param eventID 事件id
     * @param calendarCallBack
     */
    fun insertRemindersAsync(eventID: Long, calendarCallBack: CalendarCallBack?) {
        val values = ContentValues().apply {
            put(CalendarContract.Reminders.EVENT_ID, eventID)
            put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        }
        asyncOperate(calendarCallBack) {
            mAsyncQueryHandler.startInsert(queryId, null, mReminderUri, values)
        }
    }

    private val reminderSelections: String = "(${CalendarContract.Reminders.EVENT_ID} = ?)"

    //需要查询的字段
    private val REMINDER_PROJECTION by lazy {
        arrayOf(CalendarContract.Reminders._ID)
    }

    /**
     * 查询提醒
     * @param eventId 事件id
     * @param calendarCallBack
     * @param isAsync 是否异步
     */
    fun queryReminders(eventId: Long, calendarCallBack: CalendarCallBack?, isAsync: Boolean = false) {
        if (isAsync) {
            asyncOperate(calendarCallBack) {
                mAsyncQueryHandler.startQuery(queryId, null, mReminderUri, REMINDER_PROJECTION, reminderSelections,
                    arrayOf(eventId.toString()), null)
            }
        } else {
            var cursor: Cursor?
            try {
                cursor =
                    mContentResolver.query(mReminderUri, REMINDER_PROJECTION, reminderSelections, arrayOf(eventId.toString()),
                        null)
                cursor?.count
            } catch (e: Exception) {
                cursor = null
            }
            calendarCallBack?.queryResult(cursor)
        }
    }

    /**
     * 删除提醒
     * @param reminderId 提醒id
     * @return 删除条目数量
     */
    fun deleteReminder(reminderId: Long): Int {
        val updateUri: Uri = ContentUris.withAppendedId(mReminderUri, reminderId)
        return mContentResolver.delete(updateUri, null, null)
    }

    /**
     * 删除提醒
     * @param reminderId 提醒id
     * @param calendarCallBack
     */
    fun deleteReminderAsync(reminderId: Long, calendarCallBack: CalendarCallBack? = null) {
        val updateUri: Uri = ContentUris.withAppendedId(mReminderUri, reminderId)
        asyncOperate(calendarCallBack) {
            mAsyncQueryHandler.startDelete(queryId, null, updateUri, null, null)
        }
    }

    private val mAsyncQueryHandler by lazy {
        @SuppressLint("HandlerLeak") object : AsyncQueryHandler(mContentResolver) {
            override fun onQueryComplete(token: Int, cookie: Any?, cur: Cursor?) {
                asyncResult(token) {
                    querySparse[token]?.queryResult(cur)
                }
            }

            override fun onInsertComplete(token: Int, cookie: Any?, uri: Uri?) {
                asyncResult(token) {
                    querySparse[token]?.insertResult(if (uri == null) -1 else ContentUris.parseId(uri))
                }
            }

            override fun onDeleteComplete(token: Int, cookie: Any?, result: Int) {
                asyncResult(token) {
                    querySparse[token]?.deleteResult(result)
                }
            }
        }
    }


    /**
     * 添加日历提醒(回调默认在子线程)
     * @param startMillis Long 事件开始时间
     * @param eventTitle String 事件标题
     * @param eventDesc String 事件描述
     * @param rRULE String 时间重复规则
     * @param endMillis Long 事件结束时间，默认为事件开始时间
     * @param calendarCallBack CalendarCallBack? 结果回调，remindResult(result),result为true表示添加提醒成功
     * @param calendarName String 日历名称，当不存在默认日历时需要提供
     * @param accountName String 日历所属账户名称，当不存在默认日历时需要提供
     * @param accountType String 日历所属类型，当不存在默认日历时需要提供
     */
    fun addCalendarReminders(startMillis: Long, eventTitle: String, eventDesc: String, rRULE: String, calendarCallBack: CalendarCallBack?, endMillis: Long = startMillis, calendarName: String = "temp_calendar", accountName: String = "temp_account_name", accountType: String = "temp_account_type") {
        thread {
            //如果之前已经存在相同title的事件，先执行删除操作
            queryEvents(object : SimpleCalendarCallBack() {
                override fun queryResult(cursor: Cursor?) {
                    cursor?.let {
                        try {
                            it.moveToFirst()
                            while (!it.isAfterLast) {
                                val title: String = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE))?:""
                                val desc: String = it.getString(it.getColumnIndex(CalendarContract.Events.DESCRIPTION))?:""
                                if (TextUtils.equals(eventTitle,title) &&  TextUtils.equals(eventDesc,desc)) {
                                    val calId: Long = it.getLong(it.getColumnIndex(CalendarContract.Events._ID)) //取得id
                                    deleteEvent(calId)
                                }
                                it.moveToNext()
                            }
                        } catch (e: Exception) {
                        }
                    }
                }
            })
            queryCalendar(object : SimpleCalendarCallBack() {
                override fun queryResult(cursor: Cursor?) {
                    try {
                        var calId = 0L
                        if (cursor != null && cursor.count > 0) {
                            if (cursor.moveToFirst()) {
                                calId = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID))
                            }
                        } else {
                            calId = insertCalendar(calendarName, accountName, accountType)
                        }
                        val eventId = insertEvent(calId, startMillis, endMillis, eventTitle, eventDesc, rRULE)
                        val reminderId = insertReminders(eventId)
                        calendarCallBack?.remindResult(reminderId != -1L)
                    } catch (e: Exception) {
                    }
                }
            })
        }

    }

}