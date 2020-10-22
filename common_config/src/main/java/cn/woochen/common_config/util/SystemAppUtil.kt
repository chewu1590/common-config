package cn.woochen.common_config.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.TextUtils
/**
 *系统应用/三方应用工具类
 *@author woochen
 *@time 2019/10/8 13:39
 */
object SystemAppUtil {

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

    /**
     * 打开微信客户端
     */
    fun openWxApp(context: Context){
        val intent = Intent()
        val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.component = cmp
        context.startActivity(intent)
    }

    /**
     * 打开微信扫一扫
     */
    fun openWeixinToQE(context: Context) {
        try {
            val intent: Intent = context.packageManager.getLaunchIntentForPackage("com.tencent.mm")
            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true)
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {

        }
    }

    /**
     * 打开qq
     */
    fun openQQ(context: Context,qqNum:String){
        try {
            val url = "mqqwpa://im/chat?chat_type=wpa&uin=${qqNum}"//uin是发送过去的qq号码
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            e.printStackTrace()
            toast("请检查是否安装QQ")
        }

    }

    /**
     *使用默认浏览器打开
     */
    fun openLink(context: Context,urlLink:String){
        val uri = Uri.parse(urlLink)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}