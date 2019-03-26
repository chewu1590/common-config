package cn.woochen.common_config.net.helper

import android.text.TextUtils
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 *自定义异常(可继承，可参考用于自定义)
 *@author woochen
 *@time 2019/3/26 11:46 AM
 */
open class DefaultException : RuntimeException {
    var code: Int = 0
    var msg: String?

    constructor(code: Int, message: String?) : super(message) {
        if (TextUtils.isEmpty(message)) this.msg = "$code" else this.msg = message
        this.code = code

    }

    constructor(throwable: Throwable) : super(throwable) {
        var code = DefaultErrorCode.UNKNOW
        var message = throwable.message
        if (TextUtils.isEmpty(message)) message = "$code"
        if (throwable is UnknownHostException) {
            code = DefaultErrorCode.UNKNOWN_HOST
        } else if (throwable is SocketTimeoutException || throwable is ConnectException) {
            code = DefaultErrorCode.TIME_OUT
            message = "网络连接超时，请重试"
        }
        handlerMoreException(throwable)
        this.code = code
        this.msg = message
    }


    /**
     * 子类重写实现
     */
    private fun handlerMoreException(throwable: Throwable) {

    }

    override fun toString(): String {
        return "code=" + code +
                ", msg='" + msg + '\''
    }
}

