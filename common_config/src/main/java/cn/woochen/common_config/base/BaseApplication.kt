package cn.woochen.common_config.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import cn.woochen.common_config.net.state.DefaultEmptyCallback
import cn.woochen.common_config.net.state.DefaultErrorCallback
import cn.woochen.common_config.net.state.DefaultLoadingCallback
import cn.woochen.common_config.net.state.DefaultLoadingHasContentCallback
import cn.woochen.common_config.util.ConfigPref
import cn.woochen.common_config.util.UserPref
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir


/**
 * 基类application
 *
 * @author chenwuchao
 * @time 2017/9/1 16:35
 * @desc
 */
open class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}
