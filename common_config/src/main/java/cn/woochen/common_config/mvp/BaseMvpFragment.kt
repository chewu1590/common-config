package cn.woochen.common_config.mvp

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.woochen.common_config.base.BaseFragment
import cn.woochen.common_config.mvp.proxy.IPresenterProxy
import cn.woochen.common_config.net.state.DefaultEmptyCallback
import cn.woochen.common_config.net.state.DefaultErrorCallback
import cn.woochen.common_config.net.state.DefaultLoadingCallback
import cn.woochen.common_config.net.state.DefaultLoadingHasContentCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * mvp fragment 基类
 * @author woochen123
 * @time 2018/1/19 15:45
 * @desc
 */

abstract class BaseMvpFragment : BaseFragment(), IBaseView {
    protected lateinit var mPresenterProxy: IPresenterProxy
    protected var loadService: LoadService<*>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenterProxy = FragmentPresenterProxyImpl(this)
        mPresenterProxy.bindPresenter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    /**
     * kotlin中在这里可以拿到正确的控件引用
     */
    protected abstract fun initData()

    override fun initLoadSir(contentView: View) {
        loadService = LoadSir.Builder().build().register(setLoadSirTarget() ?: (contentView as ViewGroup).getChildAt(0)) { retry() }
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
    }

    /**
     * 设置状态页面的目标view
     */
    protected open fun setLoadSirTarget(): Any? = null

    /**
     * 请求网络数据(错误重试执行)
     */
    protected open fun retry() {
        requestData()
    }

    /**
     * 数据请求(重试时默认会执行)
     */
    protected open fun requestData() {

    }

    override fun onDestroy() {
        mPresenterProxy.unbindPresenter()
        super.onDestroy()
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

    override fun showLoading(showContent: Boolean) {
        if (showContent) {
            loadService?.showCallback(DefaultLoadingHasContentCallback::class.java)
        } else {
            loadService?.showCallback(DefaultLoadingCallback::class.java)
        }
    }
}
