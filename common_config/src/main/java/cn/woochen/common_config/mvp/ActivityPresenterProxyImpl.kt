package cn.woochen.common_config.mvp


import cn.woochen.common_config.mvp.proxy.PresenterProxyImpl

/**
 * activity代理类
 * @author woochen123
 * @time 2018/1/19 11:55
 * @desc
 */
class ActivityPresenterProxyImpl<V : IBaseView>(view: V) : PresenterProxyImpl<V>(view)
