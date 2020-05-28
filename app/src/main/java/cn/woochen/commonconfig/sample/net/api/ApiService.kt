package cn.woochen.commonconfig.sample.net.api

import cn.woochen.common_config.net.bean.DefaultBaseBean
import cn.woochen.commonconfig.bean.TestBean
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/26 3:03 PM
 * 修改备注：
 **/
interface ApiService {

    @GET(UrlConstainer.TEST)
    fun forTest(): Observable<DefaultBaseBean<List<TestBean>>>
}