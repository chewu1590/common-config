package cn.woochen.common_config.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import cn.woochen.common_config.util.toast

object ClipboardUtil {

    /**
     * 复制到剪切板
     */
    fun copyToClipboard(context: Context, text: String?,tipStr:String = "复制成功",block:(String)->Unit = { toast(tipStr)}) {
        if (TextUtils.isEmpty(text)) return
        val clipboardManager =
            context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Label",text)
        clipboardManager.primaryClip = clip
        block(tipStr)
    }

    /**
     * 复制到文本框
     */
    fun copyToText(context: Context) :String{
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = clipboard.primaryClip
            if (clipData != null && clipData.itemCount > 0) {
                val text = clipData.getItemAt(0).text
                return text.toString()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return ""
        }
        return ""
    }

}