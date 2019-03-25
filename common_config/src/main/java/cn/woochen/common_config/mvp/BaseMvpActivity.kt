package cn.woochen.common_config.mvp

import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.WindowManager
import cn.woochen.common_config.base.BaseActivity
import cn.woochen.common_config.mvp.proxy.IPresenterProxy
import com.kingja.loadsir.callback.Callback
import com.kingja.loadsir.callback.SuccessCallback
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


/**
 * mvp activity基类
 *
 * @author woochen
 * @time 2018/1/19 11:43
 * @desc
 */

abstract class  BaseMvpActivity : BaseActivity(), IBaseView {
    protected lateinit var mPresenterProxy: IPresenterProxy
    private var loadService: LoadService<*>? = null
    private var isRetrying: Boolean = false
    private var mRetryHandler: Handler? = object : Handler() {
        override fun handleMessage(msg: Message?) {
            isRetrying = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenterProxy = ActivityPresenterProxyImpl(this)
        mPresenterProxy.bindPresenter()
        super.onCreate(savedInstanceState)
    }


    override fun initLoadLayout() {
        val onReloadListener = Callback.OnReloadListener {
            if (!isRetrying) {
                isRetrying = true
                if (mRetryHandler != null) {
                    mRetryHandler!!.sendEmptyMessageDelayed(MESSAGE_RETRY_CODE, 3000)
                    onNetReload()
                }
            }
        }
        var loadSirTarget = setLoadSirTarget()
        if (loadSirTarget == null) loadSirTarget = this
        loadService = setLoadConfit()?.register(loadSirTarget, onReloadListener)
    }



    override fun onDestroy() {
        super.onDestroy()
        mPresenterProxy.unbindPresenter()
        if (mRetryHandler != null) {
            mRetryHandler!!.removeCallbacksAndMessages(null)
            mRetryHandler = null
        }
    }



    protected open fun setLoadConfit(): LoadSir? {
        return
    }

    protected open fun setLoadSirTarget(): Any? {
        return null
    }

    /**
     * 请求网络数据(错误重试执行)
     */
    protected open fun onNetReload() {

    }


    override fun showContent() {
        loadService!!.showSuccess()
    }

    override fun showEmpty() {
        loadService!!.showCallback(EmptyCallback::class.java)
    }

    override fun showError() {
        loadService!!.showCallback(Camera.ErrorCallback::class.java)
    }

    override fun showLoading(showContent: Boolean) {
        if (showContent) {
            loadService!!.showCallback(LoadingHasContentCallback::class.java)
        } else {
            loadService!!.showCallback(LoadingCallback::class.java)
        }
    }

    companion object {
        private val MESSAGE_RETRY_CODE = 100
    }

}
