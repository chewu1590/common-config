package cn.woochen.common_config.net.bean

/**
 *设置变量默认值
 *@author woochen
 *@time 2019/4/25 18:09
 */
interface DefaultValue {
     fun <T : Any> defaultValue(any: T?, default: T) = any ?: default
}