package cn.woochen.commonconfig.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import cn.woochen.common_config.util.GlideUtil
import cn.woochen.common_config.util.SystemAppUtil
import cn.woochen.common_config.util.logee
import cn.woochen.common_config.util.toast
import cn.woochen.commonconfig.R
import kotlinx.android.synthetic.main.activity_util.*

/**
 *工具类演示
 *@author woochen
 *@time 2019/3/26 3:45 PM
 */
class UtilActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            btn_general -> {
                Toast.makeText(this, "woochen", Toast.LENGTH_SHORT).show()
            }
            btn_purple -> {
                logee("woochen")
                toast( "woochen")
            }
            btn_call -> {
                SystemAppUtil.call(this,"88888888")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_util)
        initView()
    }

    private fun initView() {
        btn_general.setOnClickListener(this)
        btn_purple.setOnClickListener(this)
        btn_call.setOnClickListener(this)
        GlideUtil.loadCircle(this,"http://pic26.nipic.com/20121227/10193203_131357536000_2.jpg",iv_img,R.mipmap.ic_launcher,R.mipmap.ic_launcher)
    }
}
