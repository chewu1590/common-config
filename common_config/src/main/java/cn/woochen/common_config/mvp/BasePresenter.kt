package cn.woochen.common_config.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Created by admin on 2018/1/18.
 */

@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V : IBaseView> : IPresenter {
    private var mView: V? = null
    lateinit var mProxyView: V //代理view，防止view被直接修改
    private var mCompositeDisposable: CompositeDisposable? = null

    fun attach(view: V) {
        this.mView = view
        //动态代理(1.当activity没有实现响应的View，而persenter调用方法时会报错 2.当activity已经销毁，而persenter仍然在调用时会报错)
        this.mProxyView =
            Proxy.newProxyInstance(view.javaClass.classLoader, view.javaClass.interfaces) { proxy, method, args ->
                if (mView != null) {
                    method.invoke(mView, *args)
                } else null
            } as V
    }

    fun deAttach() {
        mView = null
    }

    //添加指定的请求
    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null)
            mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable!!.add(disposable)
    }

    //移除指定的请求
    fun removeDisposable(disposable: Disposable) {
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.remove(disposable)
    }

    //取消所有的请求Tag
    override fun removeAllDisposable() {
        if (mCompositeDisposable != null)
            mCompositeDisposable!!.clear()
    }
}
