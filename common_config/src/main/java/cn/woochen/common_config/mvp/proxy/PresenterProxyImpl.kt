package cn.woochen.common_config.mvp.proxy


import cn.woochen.common_config.mvp.BasePresenter
import cn.woochen.common_config.mvp.IBaseView
import cn.woochen.common_config.mvp.InjectPresenter
import java.lang.reflect.ParameterizedType


/**
 * 代理实现类
 * @author woochen123
 * @time 2018/1/19 11:44
 * @desc
 */
@Suppress("UNREACHABLE_CODE", "CAST_NEVER_SUCCEEDS")
open class PresenterProxyImpl<V : IBaseView>(private var mView: V?) : IPresenterProxy {
    private val mPresenters = mutableListOf<BasePresenter<*>>()

    override fun bindPresenter() {
        //实例化persenter
        val fields = mView!!.javaClass.declaredFields
        for (field in fields) {
            val annotation = field.getAnnotation(InjectPresenter::class.java)
            if (annotation != null) {
                val typeClass = field.type as Class<out BasePresenter<*>>
                //错误的注解判断
                if (!BasePresenter::class.java.isAssignableFrom(typeClass)) {
                    throw RuntimeException("not support presenter type:" + typeClass.name)
                }
                field.isAccessible = true
                var basePresenter: BasePresenter<*>? = null
                try {
                    basePresenter = typeClass.newInstance()
                    field.set(mView, basePresenter)
                    basePresenter!!.attach(mView as Nothing)
                    mPresenters.add(basePresenter)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                checkInterfaceView(basePresenter!!)
            }
        }
    }

    /**
     * 检测是都实现了IBaseView接口
     * @param basePresenter
     * 确保实例化presenter的类也同时实现了相应的view接口
     */
    private fun checkInterfaceView(basePresenter: BasePresenter<*>) {
        //得到BasePresenter中的接口类
        val genericSuperclass = basePresenter.javaClass.genericSuperclass as ParameterizedType
        val typeArguments = genericSuperclass.actualTypeArguments
        val viewClazz = typeArguments[0] as Class<*>
        //得到view中实现的接口
        val interfaces = mView!!.javaClass.interfaces
        for (clazz in interfaces) {
            if (viewClazz.isAssignableFrom(clazz)) {
                return
            }
        }
        throw RuntimeException(mView!!.javaClass.simpleName + " must implements " + viewClazz.name)
    }

    override fun unbindPresenter() {
        for (mPresenter in mPresenters) {
            mPresenter.removeAllDisposable()
            mPresenter.deAttach()
        }
        mView = null
    }
}
