package com.jscoolstar.accountremeber.activities.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.jscoolstar.accountremeber.R
import com.jscoolstar.accountremeber.db.entity.Cate

class CateAdapter(var context:Context, var cates:List<Cate>) : BaseAdapter() {


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var holder:ViewHolder
        var v:View
        if(p1==null){
            v = LayoutInflater.from(context).inflate(R.layout.li_cate,p2,false)
            holder = ViewHolder()
            holder.textView = v.findViewById(R.id.tv_catename)
            v.tag = holder
        }else{
            v = p1
            holder = v.tag as ViewHolder
        }

        holder.textView.setText(cates.get(p0).cateName)
        return v
    }

    override fun getItem(p0: Int): Any {
        return cates.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return cates.size
    }

    class ViewHolder{
       lateinit var textView:TextView
    }
}