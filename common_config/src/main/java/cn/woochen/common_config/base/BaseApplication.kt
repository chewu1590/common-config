package cn.woochen.common_config.base

import android.app.Application
import android.content.Context
import cn.woochen.common_config.net.DefaultRetrofitUtil
import cn.woochen.common_config.net.state.DefaultLoadingCallback
import cn.woochen.common_config.net.state.DefaultEmptyCallback
import cn.woochen.common_config.net.state.DefaultErrorCallback
import cn.woochen.common_config.net.state.DefaultLoadingHasContentCallback
import cn.woochen.common_config.util.UserPref
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadSir
import retrofit2.Retrofit


/**
 * 基类application
 *
 * @author chenwuchao
 * @time 2017/9/1 16:35
 * @desc
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var context:Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initLoadSir()
        initSp()
    }



    private fun initSp() {
        UserPref.setContext(this)
    }

    private fun initLoadSir() {
        LoadSir.beginBuilder()
            .addCallback(DefaultErrorCallback())
            .addCallback(DefaultEmptyCallback())
            .addCallback(DefaultLoadingCallback())
            .addCallback(DefaultLoadingHasContentCallback())
            .setDefaultCallback(SuccessCallback::class.java)
            .commit()
    }
}
