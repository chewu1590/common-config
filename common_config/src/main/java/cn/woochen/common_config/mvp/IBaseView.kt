package cn.woochen.common_config.mvp

/**
 * Created by admin on 2018/1/18.
 */

interface IBaseView {
    fun showLoading(showContent: Boolean)
    fun showContent()
    fun showError()
    fun showEmpty()
}
