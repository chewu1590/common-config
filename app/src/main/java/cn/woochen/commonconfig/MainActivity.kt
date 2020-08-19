package cn.woochen.commonconfig

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.woochen.common_config.util.toast
import cn.woochen.commonconfig.adapter.MainAdapter
import cn.woochen.commonconfig.sample.PermissonActivity
import cn.woochen.commonconfig.sample.UtilActivity
import cn.woochen.commonconfig.sample.net.NetActivity
import cn.woochen.commonconfig.sample.tab.SwitchTabActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var exitTime: Long = 0

    private val mItemNames = mutableListOf("工具类演示","网络请求演示","权限请求","页签切换")
    private val mMainAdapter by lazy {
        MainAdapter(this,mItemNames)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        initView()
    }

    private fun initData() {

    }

    private fun initView() {
        rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this,
            androidx.recyclerview.widget.RecyclerView.VERTICAL, false)
        rv.adapter = mMainAdapter
        mMainAdapter.itemClickListener = object: MainAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                itemClickEvent(position)
            }
        }

    }

    private fun itemClickEvent(position: Int) {
        when (position) {
            0 -> {
                start(UtilActivity::class.java)
            }
            1 -> {
                start(NetActivity::class.java)
            }
            2 -> {
                start(PermissonActivity::class.java)
            }
            3 -> {
                start(SwitchTabActivity::class.java)
            }
        }
    }


   private fun start(clazz:Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            toast("再按一下退出程序")
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            System.exit(0)
        }
    }



}
