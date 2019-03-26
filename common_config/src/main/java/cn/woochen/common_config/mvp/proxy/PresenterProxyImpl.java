package cn.woochen.common_config.mvp.proxy;

import cn.woochen.common_config.mvp.BasePresenter;
import cn.woochen.common_config.mvp.IBaseView;
import cn.woochen.common_config.mvp.InjectPresenter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 代理实现类
 * @author woochen123
 * @time 2018/1/19 11:44
 * @desc
 */
public class PresenterProxyImpl<V extends IBaseView> implements IPresenterProxy {
    private V mView;
    private List<BasePresenter> mPresenters;

    public PresenterProxyImpl(V view) {
        this.mView = view;
        mPresenters = new ArrayList<>();
    }

    @Override
    public void bindPresenter() {
        //实例化persenter
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter annotation = field.getAnnotation(InjectPresenter.class);
            if (annotation != null) {
                Class<? extends BasePresenter> typeClass = (Class<? extends BasePresenter>) field.getType();
                //错误的注解判断
                if (!BasePresenter.class.isAssignableFrom(typeClass)) {
                    throw new RuntimeException("not support presenter type:"+typeClass.getName());
                }
                field.setAccessible(true);
                BasePresenter basePresenter =null;
                try {
                    basePresenter = typeClass.newInstance();
                    field.set(mView, basePresenter);
                    basePresenter.attach(mView);
                    mPresenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                checkInterfaceView(basePresenter);
            }
        }
    }

    /**
     * 检测是都实现了IBaseView接口
     * @param basePresenter
     * 确保实例化presenter的类也同时实现了相应的view接口
     */
    private void checkInterfaceView(BasePresenter basePresenter) {
        //得到BasePresenter中的接口类
        ParameterizedType genericSuperclass = (ParameterizedType) basePresenter.getClass().getGenericSuperclass();
        Type[] typeArguments = genericSuperclass.getActualTypeArguments();
        Class viewClazz = (Class) typeArguments[0];
        //得到view中实现的接口
        Class<?>[] interfaces = mView.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            if(viewClazz.isAssignableFrom(clazz)){
                return;
            }
        }
        throw new RuntimeException(mView.getClass().getSimpleName()+" must implements "+ viewClazz.getName());
    }

    @Override
    public void unbindPresenter() {
        for (BasePresenter mPresenter : mPresenters) {
            mPresenter.removeAllDisposable();
            mPresenter.deAttach();
        }
        mView = null;
    }
}
