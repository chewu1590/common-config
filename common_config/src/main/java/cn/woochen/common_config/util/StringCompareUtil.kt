package cn.woochen.common_config.util

/**
 *字符串工具类
 *@author woochen
 *@time 2019/9/11 9:18
 */
object StringCompareUtil {

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
                if (originalArr[index].toInt() > desArr[index].toInt()){
                    return true
                }
            } catch (e: Exception) {
                return false
            }
        }
        return originalArr.size > desArr.size
    }
}