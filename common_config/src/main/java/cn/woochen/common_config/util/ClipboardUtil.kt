package cn.woochen.common_config.util

import android.content.ClipboardManager
import android.content.Context

object ClipboardUtil {


    /**
     * 将粘贴板内容复制到文本框
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