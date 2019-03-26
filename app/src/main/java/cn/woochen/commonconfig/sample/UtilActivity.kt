package cn.woochen.commonconfig.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
    }
}
