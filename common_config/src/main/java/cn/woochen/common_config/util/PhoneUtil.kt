package cn.woochen.common_config.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
/**
 *电话工具类
 *@author woochen
 *@time 2019/10/8 13:39
 */
object PhoneUtil {

    /**
     *拨号
     *@author woochen
     *@time 2019/5/8 16:36
     */
    fun call(context: Context,phone: String?) {
        if (TextUtils.isEmpty(phone)) return
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("tel:$phone")
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}