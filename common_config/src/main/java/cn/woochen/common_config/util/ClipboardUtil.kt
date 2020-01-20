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
    fun copyToClipboard(context: Context, text: String?,tipStr:String = "复制成功") {
        if (TextUtils.isEmpty(text)) return
        val clipboardManager =
            context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Label",text)
        clipboardManager.primaryClip = clip
        toast(tipStr)
    }

}