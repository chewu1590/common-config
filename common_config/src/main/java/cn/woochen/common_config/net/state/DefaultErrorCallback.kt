package cn.woochen.common_config.net.state


import android.content.Context
import android.view.View
import cn.woochen.common_config.R
import com.kingja.loadsir.callback.Callback


/**
 * 错误页面回调(可作为参考用于自定义)
 *
 * @author woochen
 * @time 2018/9/12 10:59
 */

class DefaultErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.default_layout_error
    }

    override fun onViewCreate(context: Context?, view: View?) {

    }
}
