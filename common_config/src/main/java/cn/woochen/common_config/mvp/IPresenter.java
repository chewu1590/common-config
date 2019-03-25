package cn.woochen.common_config.mvp;


/**
 * Created by Administrator on 2018/8/23.
 */

public interface IPresenter {
    //添加指定的请求
    void addDisposable(Disposable disposable);
    //移除指定的请求
    void removeDisposable(Disposable disposable);
    //取消所有请求
    void removeAllDisposable();
}
