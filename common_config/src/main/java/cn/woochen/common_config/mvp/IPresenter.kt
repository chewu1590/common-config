package cn.woochen.common_config.mvp


import io.reactivex.disposables.Disposable

/**
 * Created by Administrator on 2018/8/23.
 */

interface IPresenter {
    //添加指定的请求
    fun addDisposable(disposable: Disposable)

    //移除指定的请求
    fun removeDisposable(disposable: Disposable)

    //取消所有请求
    fun removeAllDisposable()
}
