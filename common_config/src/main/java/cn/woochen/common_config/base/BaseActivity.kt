package cn.woochen.common_config.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


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

}
