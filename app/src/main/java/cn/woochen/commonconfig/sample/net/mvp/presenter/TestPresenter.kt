package cn.woochen.commonconfig.sample.net.mvp.presenter

import cn.woochen.common_config.mvp.BasePresenter
import cn.woochen.common_config.net.helper.DefaultBaseObserver
import cn.woochen.common_config.net.helper.DefaultException
import cn.woochen.common_config.util.toast
import cn.woochen.commonconfig.bean.TestBean
import cn.woochen.commonconfig.sample.net.mvp.contract.TestContract
import cn.woochen.commonconfig.sample.net.mvp.model.TestModel

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/26 3:23 PM
 * 修改备注：
 **/
class TestPresenter : BasePresenter<TestContract.ITestView>(), TestContract.ITestPresenter {
    private val mTestModel: TestModel = TestModel()

    override fun forTest() {
        val rxBaseObserver = object : DefaultBaseObserver<List<TestBean>>() {
            override fun onStart() {
                view.showLoading(true)
            }

            override fun success(data: List<TestBean>?) {
                toast(data.toString())
                view.testSuc()
                view.showContent()
            }

            override fun fail(e: DefaultException) {
                view.testFail()
                view.showError()
            }
        }
        mTestModel.forTest(rxBaseObserver)
        addDisposable(rxBaseObserver)
    }
}