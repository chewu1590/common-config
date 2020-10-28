package cn.woochen.common_config.util

import android.text.TextUtils
import android.widget.Toast

/**
 *
 * 类描述：toast工具类
 * 创建人：woochen
 * 创建时间：2020-04-14 17:53
 * 修改备注：
 **/
fun Any.toast( msg: String?) {
    if (TextUtils.isEmpty(msg)) return
    try {
        Toast.makeText(UiUtils.context, "", Toast.LENGTH_SHORT).apply {
            setText(msg)
        }.show()
    } catch (e: Exception) {
    }
}

fun Any.toast(stringId: Int) {
    try {
        toast(UiUtils.context.getString(stringId))
    } catch (e: Exception) {
    }
}