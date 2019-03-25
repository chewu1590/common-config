package cn.woochen.common_config

import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.widget.Toast


/**
 * info print utils
 *@author woochen
 *@time 2019/3/15 6:50 PM
 */
fun Any.toast(context: Context, msg: String?) {
    if (TextUtils.isEmpty(msg)) return
    Constant.toast?.apply {
        setText(msg)
        show()
    } ?: run {
        Toast.makeText(context.applicationContext, null, Toast.LENGTH_SHORT).apply {
            setText(msg)
            Constant.toast = this
        }.show()
    }
}


fun Any.toast(context: Context, stringId: Int) {
    toast(context, context.applicationContext.getString(stringId))
}

fun Any.logee(msg: String) {
    Log.e(javaClass.simpleName, msg)
}

fun Any.soutt(msg: String) {
    System.err.println("${javaClass.simpleName} ->" + msg)
}
