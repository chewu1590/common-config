package cn.woochen.common_config.util

import java.text.DecimalFormat
import kotlin.math.floor

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/10/28 3:36 PM
 * 修改备注：
 **/

/**
 * 保留指定小数位，
 * @param count 小数点后保留位数,小于零返回自身,等于0向下取整，大于零舍掉多余位，不足补零
 */
fun Double.saveCount(count: Int): String {
    if (count < 0) return this.toString()
    if (count == 0) return floor(this).toInt().toString()
    val doubleStr = this.toString()
    if (doubleStr.contains(".")){
        val split = doubleStr.split(".")
        if (split[1].length > count){
            return "${split[0]}.${split[1].substring(0,count)}"
        }else{
            var temp = StringBuffer()
            for(index in 0 until count-split[1].length) temp.append(0)
            return "${split[0]}.${split[1]}${temp}"
        }
    }
    val pattern = StringBuffer("#.")
    repeat(count){
        pattern.append("#")
    }
    var result = DecimalFormat(pattern.toString()).format(this)
    if (this < 0) result = "0$result"
    return result
}