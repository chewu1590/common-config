package cn.woochen.common_config.helper

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 *自动处理生命周期的Handler
 *@author woochen
 *@time 2020/8/19 11:32 AM
 */
class LifecycleHandler : Handler, LifecycleObserver {
    private var lifecycleOwner: LifecycleOwner

    constructor(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
        addObserver()
    }

    constructor(callback: Callback?, lifecycleOwner: LifecycleOwner) : super(callback) {
        this.lifecycleOwner = lifecycleOwner
        addObserver()
    }

    constructor(looper: Looper?, lifecycleOwner: LifecycleOwner) : super(looper) {
        this.lifecycleOwner = lifecycleOwner
        addObserver()
    }

    constructor(looper: Looper?, callback: Callback?, lifecycleOwner: LifecycleOwner) : super(looper, callback) {
        this.lifecycleOwner = lifecycleOwner
        addObserver()
    }

    private fun addObserver() {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        removeCallbacksAndMessages(null)
        lifecycleOwner.lifecycle.removeObserver(this)
    }
}