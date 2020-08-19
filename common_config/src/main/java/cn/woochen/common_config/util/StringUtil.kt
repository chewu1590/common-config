package cn.woochen.common_config.util

import java.text.DecimalFormat

/**
 *字符串工具类
 *@author woochen
 *@time 2019/9/11 9:18
 */
object StringUtil {

    /**
     * 比较两个字符串的大小 such as 1.0.2=>2.3.4
     * @param originStr 原始字符串
     * @param desStr 目标字符串
     * @return true originStr比destr大
     * @return false originStr比destr小，字符串不符合规则
     */
    fun compare(originStr:String,desStr:String):Boolean{
        val originalArr = originStr.split(".")
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
     * 将double保留count位小数
     */
    fun toDouble(double: Double, count: Int): String {
        if (double <= 0) {
            return "0.00"
        }
        val doubleStr = double.toString()
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
        var pattern = StringBuffer("#.")
        for (index in 0 until count) pattern.append("#")
        var result = DecimalFormat(pattern.toString()).format(double)
        if (double < 0) result = "0" + result
        return result
    }

    /**
     * 将double保留count位小数
     */
    fun toDouble(double: String, count: Int) : String = toDouble(double.toDouble(),count)

    /**
     * 字符串部分替换为特殊符号
     */
    fun replaceByStar(message: String, startIndex: Int, replaceNum: Int): String {
        val replaceSymbol = "*"//替换符号
        val stringBuilder = StringBuilder()
        for (i in message.indices) {
            val number = message[i]
            if (i >= startIndex && i < startIndex + replaceNum) {
                stringBuilder.append(replaceSymbol)
            } else {
                stringBuilder.append(number)
            }
        }
        return stringBuilder.toString()
    }
}