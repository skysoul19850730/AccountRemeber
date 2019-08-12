package com.jscoolstar.accountremeber.activities.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.dataprovider.dataentity.ExtraColumn

/**
 * Created by Administrator on 2018/4/17.
 */
class ExtraColumnAdapter(val mContext: Context, var mList: ArrayList<ExtraColumn>, var columnClickListerner: ExtraColumnClickListener) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        var mHolder = holder as MHoler
        var extraColumn = mList[position]
        mHolder.tv_extra_title.text = extraColumn.key
        mHolder.tv_extra_value.text = extraColumn.value
        mHolder.tv_extra_title.setOnClickListener{
            columnClickListerner.onItemClick(extraColumn)
        }
        mHolder.tv_extra_value.setOnClickListener{ columnClickListerner.onItemClick(extraColumn) }
        mHolder.btn_delete.setOnClickListener{ columnClickListerner.onItemDeleteClick(extraColumn) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.li_extra, parent, false)
        return MHoler(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface ExtraColumnClickListener {
        fun onItemClick(extraColumn: ExtraColumn)
        fun onItemDeleteClick(extraColumn: ExtraColumn)
    }

    class MHoler(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var btn_delete: ImageView = view.findViewById(R.id.btn_delete)
        var tv_extra_title: TextView = view.findViewById(R.id.tv_extra_title)
        var tv_extra_value: TextView = view.findViewById(R.id.tv_extra_value)
    }
}