package cn.woochen.commonconfig.sample.api

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/26 3:14 PM
 * 修改备注：
 **/
object UrlConstainer {
     var appBaseUrl = ""

    const val APP_TEST_URL = "http://gank.io/api/" //测试环境
    const val APP_RELEASE_URL = "http://gank.io/api/"  //线上环境


//    api
    const val TEST = "today"  //测试
}