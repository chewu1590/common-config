package com.woochen.baselibrary.base.mvp

import android.hardware.Camera
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.woochen.common_config.base.BaseFragment
import cn.woochen.common_config.mvp.FragmentPresenterProxyImpl
import cn.woochen.common_config.mvp.IBaseView


/**
 * mvp fragment 基类
 * @author woochen123
 * @time 2018/1/19 15:45
 * @desc
 */

abstract class BaseMvpFragment : BaseFragment(), IBaseView {
    lateinit var mPresenterProxy: IPresenterProxy
    private var loadService: LoadService<*>? = null
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
        initView()
    }

    protected abstract fun initView()

    override fun initLoadSir(rootView: View): View {
        loadService = LoadSir.getDefault().register(rootView) {
            if (!isRetrying) {
                isRetrying = true
                if (mRetryHandler != null) {
                    mRetryHandler!!.sendEmptyMessageDelayed(MESSAGE_RETRY_CODE, 3000)
                    requestData()
                }
            } else {
                //                    ToastUtil.showToast(R.string.much_operate);
            }
        }
        //        showLoading(true);
        return loadService!!.loadLayout
    }

    /**
     * 数据请求(重试)
     */
    protected fun requestData() {

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
        private val MESSAGE_RETRY_CODE = 1000
    }

}
