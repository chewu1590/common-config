package cn.woochen.commonconfig.sample.net.mvp.contract

import cn.woochen.common_config.mvp.IBaseView

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/26 3:22 PM
 * 修改备注：
 **/
class TestContract {
    interface ITestPresenter {
        fun forTest()
    }

    interface ITestView : IBaseView {
        fun testSuc()
        fun testFail()
    }
}