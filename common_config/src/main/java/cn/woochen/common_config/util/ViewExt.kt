package cn.woochen.common_config.util

import android.annotation.SuppressLint
import android.view.View
import cn.woochen.common_config.helper.OnSingleClickListener
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * 单机事件(默认间隔为1秒)
 */
@SuppressLint("CheckResult")
fun View.setOnSingleClickListener(singleClickListener: OnSingleClickListener?, timeSecond:Long = 1){
    RxView.clicks(this)
        .throttleFirst(timeSecond, TimeUnit.SECONDS)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe {
            singleClickListener?.onSingleClick(this)
        }
}