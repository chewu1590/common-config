package cn.woochen.commonconfig.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.woochen.commonconfig.R

/**
 *
 * 类描述：
 * 创建人：woochen
 * 创建时间：2019/3/14 5:53 PM
 * 修改备注：
 **/
class MainAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> {

    private var mContext: Context
    val mDatas = mutableListOf<String>()
    var itemClickListener: OnItemClickListener? = null

    constructor(context: Context,datas: MutableList<String>) : super() {
        mContext = context
        mDatas.clear()
        mDatas.addAll(datas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent,false)
        return MyViewHold(view)
    }

    override fun getItemCount(): Int = mDatas.size

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHold) {
            val tvText = holder.itemView.findViewById<TextView>(R.id.tv_text)
            tvText.text = mDatas[position]
            holder.itemView.setOnClickListener { itemClickListener?.onItemClick(position) }
        }
    }


    class MyViewHold : androidx.recyclerview.widget.RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}