package cn.woochen.common_config.mvp


import cn.woochen.common_config.mvp.proxy.PresenterProxyImpl

/**
 * fragment代理类
 * @author woochen123
 * @time 2018/1/19 11:55
 * @desc
 */
class FragmentPresenterProxyImpl<V : IBaseView>(view: V) : PresenterProxyImpl<V>(view)
