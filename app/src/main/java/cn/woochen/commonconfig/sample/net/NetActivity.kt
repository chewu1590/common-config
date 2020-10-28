package cn.woochen.commonconfig.sample.net

import android.os.SystemClock
import android.view.View
import cn.woochen.common_config.mvp.BaseMvpActivity
import cn.woochen.common_config.mvp.InjectPresenter
import cn.woochen.commonconfig.R
import cn.woochen.commonconfig.sample.net.mvp.contract.TestContract
import cn.woochen.commonconfig.sample.net.mvp.presenter.TestPresenter
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_net.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * 网络请求演示
 */
class NetActivity : BaseMvpActivity(), View.OnClickListener ,TestContract.ITestView{



    override fun testSuc() {

    }

    override fun testFail() {
    }


    override fun onClick(v: View?) {
        when (v) {
            btn_request -> {
                requestData()
            }
            btn_suc -> {
                monitorRequestSuc()
            }
        }
    }

    private fun monitorRequestSuc() {
        showLoading(false)
        thread {
            SystemClock.sleep(3000)
            runOnUiThread {
                showContent()
            }
        }
    }

    @InjectPresenter
    private lateinit var mTestPresenter: TestPresenter

    override fun setContentView(): Int = R.layout.activity_net

    override fun initData() {
        btn_request.setOnClickListener(this)
        btn_suc.setOnClickListener(this)
    }

    override fun requestData() {
        mTestPresenter.forTest()
    }

}
