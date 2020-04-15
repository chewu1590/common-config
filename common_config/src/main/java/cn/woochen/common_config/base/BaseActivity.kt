package cn.woochen.common_config.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity


/**
 * Activity基类(不要修改)
 * Created by woochen123 on 2017/7/26.
 */

abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        setContentView(setContentView())
        initLoadLayout()
        initData()
    }

    /**
     * 初始化状态布局
     */
    protected abstract fun initLoadLayout()

    protected abstract fun initData()

    protected abstract fun setContentView(): Int

    fun startActivity(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (!isFinishing) {
            back()
        }
    }

    protected open fun back() {
        finish()
    }

    /**
     * 处理 Activity 多重跳转
     */
    override fun startActivityForResult(intent: Intent?, requestCode: Int, options: Bundle?) {
        if (startActivitySelfCheck(intent)) {
            // 查看源码得知 startActivity 最终也会调用 startActivityForResult
            super.startActivityForResult(intent, requestCode, options)
        }
    }

    private var mStartActivityTag: String? = null
    private var mStartActivityTime: Long = 0

    /**
     * 检查当前 Activity 是否重复跳转了，不需要检查则重写此方法并返回 true 即可
     *
     * @param intent          用于跳转的 Intent 对象
     * @return                检查通过返回true, 检查不通过返回false
     */
    protected fun startActivitySelfCheck(intent: Intent?): Boolean {
        // 默认检查通过
        var result = true
        // 标记对象
        val tag: String?
        if (intent?.component != null) { // 显式跳转
            tag = intent.component!!.className
        } else if (intent?.action != null) { // 隐式跳转
            tag = intent.action
        } else { // 其他方式
            return true
        }
        if (tag == mStartActivityTag  && mStartActivityTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false
        }
        mStartActivityTag = tag
        mStartActivityTime = SystemClock.uptimeMillis()
        return result
    }

}
