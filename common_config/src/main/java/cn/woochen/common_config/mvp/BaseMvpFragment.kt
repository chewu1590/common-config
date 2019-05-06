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
    private var isRetrying: Boolean = false
    private var mRetryHandler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message) {
            isRetrying = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenterProxy = FragmentPresenterProxyImpl(this)
        mPresenterProxy.bindPresenter()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
    }

    protected abstract fun initData()

    override fun initLoadSir(contentView: View): View {
        var loadSirTarget = setLoadSirTarget()
        if (loadSirTarget == null) loadSirTarget = contentView
        loadService = LoadSir.getDefault().register(loadSirTarget) {
            if (!isRetrying) {
                isRetrying = true
                if (mRetryHandler != null) {
                    mRetryHandler!!.sendEmptyMessageDelayed(MESSAGE_RETRY_CODE, 3000)
                    requestData()
                }
            }
        }
        return loadService!!.loadLayout
    }

    /**
     * 设置状态页面的目标view
     */
    protected open fun setLoadSirTarget(): Any? = null

    /**
     * 数据请求(重试时默认会执行)
     */
    protected open fun requestData() {

    }

    override fun onDestroy() {
        mPresenterProxy.unbindPresenter()
        super.onDestroy()
        if (mRetryHandler != null) {
            mRetryHandler!!.removeCallbacksAndMessages(null)
            mRetryHandler = null
        }
    }


    override fun showContent() {
        loadService!!.showSuccess()
    }

    override fun showEmpty() {
        loadService!!.showCallback(DefaultEmptyCallback::class.java)
    }

    override fun showError() {
        loadService!!.showCallback(DefaultErrorCallback::class.java)
    }

    override fun showLoading(showContent: Boolean) {
        if (showContent) {
            loadService!!.showCallback(DefaultLoadingHasContentCallback::class.java)
        } else {
            loadService!!.showCallback(DefaultLoadingCallback::class.java)
        }
    }

    companion object {
        private val MESSAGE_RETRY_CODE = 1000
    }

}
