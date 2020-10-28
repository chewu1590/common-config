package cn.woochen.common_config


import android.view.View
import cn.woochen.common_config.helper.OnSingleClickListener
import cn.woochen.common_config.util.UiUtils
import cn.woochen.common_config.util.saveCount
import cn.woochen.common_config.util.setOnSingleClickListener

/**
 * 类描述：模块测试
 * 创建人：woochen
 * 创建时间：2020/10/28 3:51 PM
 * 修改备注：
 */
internal object Test {

    @JvmStatic
    fun main(args: Array<String>) {
        println(0.02.saveCount(5))
        println(13.1569.saveCount(5))
        View(UiUtils.context).setOnSingleClickListener(OnSingleClickListener {

        })
    }
}