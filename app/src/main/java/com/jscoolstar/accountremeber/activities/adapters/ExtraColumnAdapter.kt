package com.jscoolstar.accountremeber.activities.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.db.entity.ExtraColumn
import kotlinx.android.synthetic.main.li_extra.view.*
import org.jetbrains.annotations.NotNull

/**
 * Created by Administrator on 2018/4/17.
 */
class ExtraColumnAdapter(val mContext: Context, var mList: ArrayList<ExtraColumn>, var columnClickListerner: ExtraColumnClickListerner) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var mHolder = holder as MHoler
        var extraColumn = mList[position]
        mHolder.tv_extra_title.text = extraColumn.key
        mHolder.tv_extra_value.text = extraColumn.value
        mHolder.tv_extra_title.setOnClickListener({
            columnClickListerner.onItemClick(extraColumn)
        })
        mHolder.tv_extra_value.setOnClickListener({ columnClickListerner.onItemClick(extraColumn) })
        mHolder.btn_delete.setOnClickListener({ columnClickListerner.onItemDeleteClick(extraColumn) })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.li_extra, parent, false)
        return MHoler(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface ExtraColumnClickListerner {
        fun onItemClick(extraColumn: ExtraColumn)
        fun onItemDeleteClick(extraColumn: ExtraColumn)
    }

    class MHoler(val view: View) : RecyclerView.ViewHolder(view) {
        var btn_delete: ImageView
        var tv_extra_title: TextView
        var tv_extra_value: TextView

        init {
            btn_delete = view.findViewById<ImageView>(R.id.btn_delete)
            tv_extra_title = view.findViewById<TextView>(R.id.tv_extra_title)
            tv_extra_value = view.findViewById<TextView>(R.id.tv_extra_value)
        }
    }
}