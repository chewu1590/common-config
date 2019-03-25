package cn.woochen.commonconfig.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import cn.woochen.common_config.logee
import cn.woochen.common_config.toast
import cn.woochen.commonconfig.R
import kotlinx.android.synthetic.main.activity_common_lib.*


class CommonLibActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            btn_general -> {
                Toast.makeText(this, "woochen", Toast.LENGTH_SHORT).show()
            }
            btn_purple -> {
                logee("woochen")
                toast(this, "woochen")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_lib)
        initView()
    }

    private fun initView() {
        btn_general.setOnClickListener(this)
        btn_purple.setOnClickListener(this)
    }
}
