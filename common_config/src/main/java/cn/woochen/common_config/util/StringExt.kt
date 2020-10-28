package cn.woochen.common_config.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.text.Html
import android.text.TextUtils

/**
 *String扩展类
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/10/22 10:50 AM
 * 修改备注：
 **/

/**
 * 以html的格式展示
 */
fun String.toHtml(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY, null, null)
    }else{
        Html.fromHtml(this)
    }
}


/**
 * 复制到剪切板
 */
fun String.copyToClipboard(context: Context, tipStr:String = "复制成功", block:(String)->Unit = { toast(tipStr)}) {
    if (TextUtils.isEmpty(this)) return
    val clipboardManager =
        context.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Label",this)
    clipboardManager.primaryClip = clip
    block(tipStr)
}

/**
 * 比较特定格式的两个字符串的大小 such as 1.0.2 < 2.3.4; 1.0.2.2 > 3.4.5
 * @param desStr 目标字符串
 * @return true 比destr大
 * @return false 比destr小，字符串不符合规则
 */
fun String.isOverStr(desStr:String):Boolean{
    val originalArr = this.split(".")
    val desArr = desStr.split(".")
    val minSize = if (originalArr.size > desArr.size) desArr.size else originalArr.size
    for (index in 0 until minSize){
        try {
            return originalArr[index].toInt() > desArr[index].toInt()
        } catch (e: Exception) {
            return false
        }
    }
    return originalArr.size >= desArr.size
}


/**
 * 字符串部分替换为特殊符号
 * @param startIndex 开始替换的索引
 * @param replaceNum 替换的个数
 * @param replaceSymbol 替换的符号
 */
fun String.replaceBySymbol(startIndex: Int, replaceNum: Int,replaceSymbol:String = "*"): String {
    val stringBuilder = StringBuilder()
    for (i in indices) {
        val number = this[i]
        if (i >= startIndex && i < startIndex + replaceNum) {
            stringBuilder.append(replaceSymbol)
        } else {
            stringBuilder.append(number)
        }
    }
    return stringBuilder.toString()
}

