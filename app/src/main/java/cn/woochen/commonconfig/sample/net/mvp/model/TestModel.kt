package cn.woochen.commonconfig.sample.net.mvp.model

import cn.woochen.common_config.net.bean.DefaultBaseBean
import cn.woochen.common_config.net.helper.RxSchedulers
import cn.woochen.commonconfig.bean.TestBean
import cn.woochen.commonconfig.sample.net.mvp.base.BaseModel
import io.reactivex.Observer

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/26 3:07 PM
 * 修改备注：
 **/
class TestModel : BaseModel() {

    /**
     * 测试
     */
    fun forTest(observer: Observer<DefaultBaseBean<List<TestBean>>>) {
        rxApi.forTest()
            .compose<DefaultBaseBean<List<TestBean>>>(RxSchedulers.io_main())
            .subscribe(observer)
    }
}