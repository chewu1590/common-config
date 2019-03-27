package cn.woochen.common_config.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


/**
 * 类描述：fragemnt切换辅助类
 * 创建人：woochen
 * 创建时间：2017/9/7 21:56
 * getFragments()方法需要先add一个fragment才能使用
 */
class FragmentManagerHelper(private var mFragmentManager: FragmentManager, private val mContainerRes: Int) {

    /**
     * 添加fragment
     *
     * @param fragment
     */
    fun addFragment(fragment: Fragment, fragmentTag: String) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        if (!fragment.isAdded && null == mFragmentManager.findFragmentByTag(fragmentTag)) {
            fragmentTransaction.add(mContainerRes, fragment, fragmentTag)
        }
        fragmentTransaction.commit()
    }

    /**
     * 显示fragment
     * desc : 1.需要先add一个fragment
     */
    fun swithFragment(fragment: Fragment) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        //隐藏所有fragment
        val childfragments = mFragmentManager.fragments
        for (childFragment in childfragments) {
            fragmentTransaction.hide(childFragment)
        }
        //存在就直接显示，没有加添加到FragmentManager中
        if (mFragmentManager.fragments.contains(fragment)) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(mContainerRes, fragment)
        }
        fragmentTransaction.commit()
    }


    /**
     * 切换fragment(避免状态异常)
     * desc : 1.需要先add一个fragment
     */
    fun swithFragmentAllowingStateLoss(fragment: Fragment, fragmentTag: String) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        //隐藏所有fragment
        val childfragments = mFragmentManager.fragments
        for (childFragment in childfragments) {
            fragmentTransaction.hide(childFragment)
        }
        if (!fragment.isAdded && null == mFragmentManager.findFragmentByTag(fragmentTag)) {
            fragmentTransaction.add(mContainerRes, fragment, fragmentTag)
        } else {
            fragmentTransaction.show(fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }


    /**
     * replace切换
     *
     * @param fragment
     */
    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(mContainerRes, fragment).commit()
    }

    /**
     * replace切换（避免状态异常）
     */
    fun replaceFragmentAllowingStateLoss(fragment: Fragment) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(mContainerRes, fragment).commitAllowingStateLoss()
    }

    /**
     * replace切换（避免状态异常）
     */
    fun replaceFragmentAllowingStateLoss(fragment: Fragment,tag :String) {
        val fragmentTransaction = mFragmentManager.beginTransaction()
        fragmentTransaction.replace(mContainerRes, fragment,tag).commitAllowingStateLoss()
    }

    /**
     * 清空所有的frament
     */
    fun clear() {
        mFragmentManager.fragments.clear()
    }
}
