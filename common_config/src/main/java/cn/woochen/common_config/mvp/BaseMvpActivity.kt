package cn.woochen.common_config.mvp

import android.os.Bundle
import cn.woochen.common_config.base.BaseActivity
import cn.woochen.common_config.mvp.proxy.IPresenterProxy
import cn.woochen.common_config.net.state.DefaultLoadingCallback
import cn.woochen.common_config.net.state.DefaultLoadingHasContentCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * mvp activity基类
 *
 * @author woochen
 * @time 2018/1/19 11:43
 * @desc
 */

abstract class BaseMvpActivity : BaseActivity(), IBaseView {
    private lateinit var mPresenterProxy: IPresenterProxy
    protected var loadService: LoadService<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenterProxy = ActivityPresenterProxyImpl(this)
        mPresenterProxy.bindPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun initLoadLayout() {
        loadService = LoadSir.Builder().build().register(setLoadSirTarget() ?: this) { retry() }
        setStatusCallbacks()
    }

    /**
     * 设置状态页回调
     * @desc 默认设置两种回调，子类可进行覆盖重写
     */
    protected open fun setStatusCallbacks() {
        loadService?.loadLayout?.apply {
            setupCallback(DefaultLoadingCallback())
            setupCallback(DefaultLoadingHasContentCallback())
        }
        showContent()
    }

    /**
     * 请求网络数据(错误重试执行)
     */
    protected open fun retry() {
        requestData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenterProxy.unbindPresenter()
    }

    /**
     * 设置状态页面的目标view
     */
    protected open fun setLoadSirTarget(): Any? = null

    /**
     * 网络请求
     */
    protected open fun requestData() {

    }

    override fun showLoading(showContent: Boolean) {
        if (showContent) {
            loadService?.showCallback(DefaultLoadingHasContentCallback::class.java)
        } else {
            loadService?.showCallback(DefaultLoadingCallback::class.java)
        }
    }

    override fun showContent() {
        loadService?.showSuccess()
    }

    /**
     * 默认空实现，可用于子类进行扩展
     */
    override fun showError() {

    }

    /**
     * 默认空实现，可用于子类进行扩展
     */
    override fun showEmpty() {

    }

}
