package cn.woochen.commonconfig

import cn.woochen.common_config.base.BaseApplication
import cn.woochen.common_config.net.DefaultRetrofitUtil
import cn.woochen.common_config.util.UiUtils
import cn.woochen.commonconfig.sample.net.api.UrlConstainer

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/26 2:47 PM
 * 修改备注：
 **/
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
    }


    private fun initRetrofit() {
        if (BuildConfig.DEBUG){
            UrlConstainer.appBaseUrl = UrlConstainer.APP_TEST_URL
        }else{
            UrlConstainer.appBaseUrl = UrlConstainer.APP_RELEASE_URL
        }
        DefaultRetrofitUtil.getInstance()
            .init(UrlConstainer.appBaseUrl)
    }


}

