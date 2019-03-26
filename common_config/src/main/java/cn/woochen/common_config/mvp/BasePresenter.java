package cn.woochen.common_config.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by admin on 2018/1/18.
 */

public abstract class BasePresenter<V extends IBaseView>  implements IPresenter {
    protected V mView;
    protected V mProxyView;

    public void attach(V view) {
        this.mView = view;
        //动态代理(1.当activity没有实现响应的View，而persenter调用方法时会报错 2.当activity已经销毁，而persenter仍然在调用时会报错)
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (mView != null) {
                    return method.invoke(mView, args);
                }
                return null;
            }
        });
    }

    public void deAttach() {
        mView = null;
    }


    public V getView(){
        return mProxyView;
    }


    private CompositeDisposable mCompositeDisposable;

    //添加指定的请求
    @Override
    public void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null)
            mCompositeDisposable = new CompositeDisposable();
        mCompositeDisposable.add(disposable);
    }

    //移除指定的请求
    @Override
    public void removeDisposable(Disposable disposable) {
        if (mCompositeDisposable != null)
            mCompositeDisposable.remove(disposable);
    }

    //取消所有的请求Tag
    @Override
    public void removeAllDisposable() {
        if (mCompositeDisposable != null)
            mCompositeDisposable.clear();
    }
}
