package cn.woochen.common_config.mvp;


import cn.woochen.common_config.mvp.proxy.PresenterProxyImpl;

/**
 *activity代理类
 *@author woochen123
 *@time 2018/1/19 11:55
 *@desc
 */
public class ActivityPresenterProxyImpl<V extends IBaseView> extends PresenterProxyImpl<V> {
    public ActivityPresenterProxyImpl(V view) {
        super(view);
    }
}
