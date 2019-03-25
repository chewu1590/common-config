package cn.woochen.common_config.mvp;


/**
 *fragment代理类
 *@author woochen123
 *@time 2018/1/19 11:55
 *@desc
 */
public class FragmentPresenterProxyImpl<V extends IBaseView> extends PresenterProxyImpl<V> {
    public FragmentPresenterProxyImpl(V view) {
        super(view);
    }
}
