package cn.woochen.common_config

import android.content.Context
import androidx.startup.AppInitializer
import androidx.startup.Initializer
import cn.woochen.common_config.util.ConfigPref
import cn.woochen.common_config.util.UiUtils
import cn.woochen.common_config.util.UserPref

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2020/9/16 5:24 PM
 * 修改备注：
 **/
class LibraryConfig : Initializer<Unit> {

    override fun create(context: Context) {
        UserPref.setContext(context)
        ConfigPref.setContext(context)
        UiUtils.setContext(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}