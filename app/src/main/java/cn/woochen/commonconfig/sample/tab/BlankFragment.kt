package cn.woochen.commonconfig.sample.tab

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.woochen.commonconfig.R
import kotlinx.android.synthetic.main.fragment_blank.*


class BlankFragment : Fragment() {

    private val mContent by lazy {
        this.arguments?.getString("arg1")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blank, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_content.text = mContent
        tv_content.setOnClickListener {
            val intent = Intent(context, EmptyActivity::class.java)
            context?.startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(text:String) = BlankFragment().apply {
            this.arguments = Bundle().apply {
                this.putString("arg1",text)
            }
        }
    }
}
