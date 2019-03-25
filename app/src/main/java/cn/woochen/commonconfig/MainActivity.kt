package cn.woochen.commonconfig

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.woochen.commonconfig.adapter.MainAdapter
import cn.woochen.commonconfig.demo.CommonLibActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mItemNames = mutableListOf<String>("Common_Lib")
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
        rv.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        rv.adapter = mMainAdapter
        mMainAdapter.itemClickListener = object: MainAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
//                Toast.makeText(this@MainActivity,mItemNames[position],Toast.LENGTH_SHORT).show()
                itemClickEvent(position)
            }
        }

    }


    private fun itemClickEvent(position: Int) {
        when (position) {
            0 -> {
                start(CommonLibActivity::class.java)
            }
            else -> {
            }
        }
    }


   private fun start(clazz:Class<*>){
        val intent = Intent(this,clazz)
        startActivity(intent)

    }


}
