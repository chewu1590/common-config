package cn.woochen.common_config.net.helper

/**
 *网络请求错误码(可继承，可作为参考用于自定义)
 *@author woochen
 *@time 2019/3/26 11:47 AM
 */
open class DefaultErrorCode {

    companion object {
        //custom code
        const val UNKNOW = 10000

        //server code
        const val UNKNOWN_HOST = 10001
        const val TIME_OUT = 10002
    }


}
