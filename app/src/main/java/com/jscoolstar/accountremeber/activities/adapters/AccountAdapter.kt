package com.jscoolstar.accountremeber.activities.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.db.entity.DMAccount

/**
 * Created by Administrator on 2018/4/16.
 */
class AccountAdapter(val mContext: Context, var mList: List<DMAccount>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface AccountListClickListerner {
        fun onItemClick(account: DMAccount)
        fun onItemLongClick(account: DMAccount)
    }

    lateinit var listerner: AccountListClickListerner

    var selectAccounts: ArrayList<DMAccount> = ArrayList()

    var mInEdit = false
        set(value) {
            field = value
            selectAccounts.clear()
            notifyDataSetChanged()
        }

    fun setList(list: List<DMAccount>) {
        mList = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mHolder: MViewHolder = holder as MViewHolder
        var account = mList[position]
        mHolder.tv_title.setText(account.platform)
        mHolder.tv_title.setOnClickListener {
            if (mInEdit) {
                if (selectAccounts.contains(account)) {
                    mHolder.cb_select.isChecked = false
                } else {
                    mHolder.cb_select.isChecked = true
                }
            } else
                listerner?.onItemClick(account)
        }
        mHolder.tv_title.setOnLongClickListener({
            listerner?.onItemLongClick(account)
            return@setOnLongClickListener true
        })

        mHolder.cb_select.visibility = (if (mInEdit) View.VISIBLE else View.GONE)

        if (mInEdit) {
            var checked = false;
            mHolder.cb_select.isChecked = selectAccounts.contains(account)
        }

        mHolder.cb_select.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    selectAccounts.add(account)
                } else selectAccounts.remove(account)
            }

        })

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.li_accout, parent, false)
        return MViewHolder(view)
    }

    class MViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        var tv_title: TextView
        var cb_select: CheckBox

        init {
            tv_title = view.findViewById<TextView>(R.id.tv_title)
            cb_select = view.findViewById<CheckBox>(R.id.cb_select)
        }
    }
}