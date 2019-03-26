package cn.woochen.common_config.net.helper

import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 *
 * @author woochen
 * @time 2018/8/23 10:51
 */
object RxSchedulers {

    fun <T,F>io_main(): ObservableTransformer<in T,out F>{

        return ObservableTransformer<T, F> { upstream ->
            upstream
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())  as ObservableSource<F>
        }
    }
}
