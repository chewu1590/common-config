package cn.woochen.common_config.util

import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import cn.woochen.common_config.Constant
import cn.woochen.common_config.base.BaseApplication


/**
 * info print utils
 *@author woochen
 *@time 2019/3/15 6:50 PM
 */
fun Any.toast( msg: String?) {
    if (TextUtils.isEmpty(msg)) return
    Constant.toast?.apply {
        setText(msg)
        show()
    } ?: run {
        Toast.makeText(BaseApplication.context, null, Toast.LENGTH_SHORT).apply {
            setText(msg)
            Constant.toast = this
        }.show()
    }
}

fun Any.toast(stringId: Int) {
    toast(BaseApplication.context.getString(stringId))
}

fun Any.logee(msg: String) {
    Log.e(javaClass.simpleName, msg)
}

fun Any.soutt(msg: String) {
    System.err.println("${javaClass.simpleName} ->" + msg)
}
