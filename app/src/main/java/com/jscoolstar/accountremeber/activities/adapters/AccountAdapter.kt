package com.jscoolstar.accountremeber.activities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.model.beans.Account

/**
 * Created by Administrator on 2018/4/16.
 */
class AccountAdapter(val mContext: Context, var mList: List<Account>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {

    interface AccountListClickListerner {
        fun onItemClick(position: Int)
        fun onItemLongClick(position: Int)
    }

    lateinit var listerner: AccountListClickListerner


    var mInEdit = false
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setList(list: List<Account>) {
        mList = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        var mHolder: MViewHolder = holder as MViewHolder
        var account = mList[position]
        mHolder.tv_title.setText(account.platform)
        mHolder.tv_title.setOnClickListener {
                listerner?.onItemClick(position)
        }
        mHolder.tv_title.setOnLongClickListener{
            listerner?.onItemLongClick(position)
            return@setOnLongClickListener true
        }

        mHolder.cb_select.visibility = (if (mInEdit) View.VISIBLE else View.GONE)

        if (mInEdit) {
            mHolder.cb_select.isChecked = account.isChecked
        }
        mHolder.cb_select.isEnabled = false
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.li_accout, parent, false)
        return MViewHolder(view)
    }

    class MViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

        var tv_title: TextView
        var cb_select: CheckBox

        init {
            tv_title = view.findViewById<TextView>(R.id.tv_title)
            cb_select = view.findViewById<CheckBox>(R.id.cb_select)
        }
    }
}