package cn.woochen.commonconfig.sample.tab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.woochen.common_config.util.FragmentManagerHelper
import cn.woochen.common_config.util.logee
import cn.woochen.commonconfig.R
import kotlinx.android.synthetic.main.activity_switch_tab.*

/**
 *tab切换演示类
 *@author woochen
 *@time 2019/8/20 11:35
 */
class SwitchTabActivity : AppCompatActivity(), View.OnClickListener {

    private val mFragmentManagerHelper by lazy {
        FragmentManagerHelper(supportFragmentManager, R.id.fl_container)
    }

    private var mTabFragment1:BlankFragment? = null
    private var mTabFragment2:BlankFragment? = null

    override fun onClick(v: View?) {
        when (v) {
            tv_tab1 -> {//页签1
                if (mTabFragment1 == null)  mTabFragment1 = BlankFragment.newInstance("标签一的内容")
                mFragmentManagerHelper.swithFragmentAllowingStateLoss(mTabFragment1!!,"1")
            }
            tv_tab2 -> {//页签2
                if (mTabFragment2 == null)  mTabFragment2 = BlankFragment.newInstance("标签二的内容")
                mFragmentManagerHelper.swithFragmentAllowingStateLoss(mTabFragment2!!,"2")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState !=null){
            logee("savedInstanceState exist")
        }else{
            logee("savedInstanceState is null")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_tab)
        initListener()
        initFragments()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        //activity 销毁的时候，不要保存状态
//        super.onSaveInstanceState(outState)
    }

    private fun initFragments() {
        logee("initFragments")
        mTabFragment1 = BlankFragment.newInstance("标签一的内容")
        mFragmentManagerHelper.addFragment(mTabFragment1!!,"1")
    }


    private fun initListener() {
        tv_tab1.setOnClickListener(this)
        tv_tab2.setOnClickListener(this)
    }
}
