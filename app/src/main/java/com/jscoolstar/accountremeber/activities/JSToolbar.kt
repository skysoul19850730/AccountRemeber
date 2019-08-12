package com.jscoolstar.accountremeber.activities

import android.content.Context
import android.os.Build
import androidx.appcompat.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import com.jscoolstar.accountremeber.R

/**
 * Created by Administrator on 2018/4/11.
 */
class JSToolbar @JvmOverloads constructor(context: Context, val attrs: AttributeSet? = null, val defStyleAttr: Int = 0) : Toolbar(context, attrs, defStyleAttr) {



    init {
        init()
    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBackgroundResource(R.color.colorPrimary)
            elevation = resources.getDimension(R.dimen.dp4)
        } else {
            setBackgroundResource(R.mipmap.navigation_bar)
        }

        var a = context.obtainStyledAttributes(attrs, R.styleable.JSToolbar, defStyleAttr, 0)
        var menuid = a.getResourceId(R.styleable.JSToolbar_menu_id, 0)
        inflateMenu(menuid)

        setOnMenuItemClickListener {
            listerner?.menuClicked(it.itemId)
            return@setOnMenuItemClickListener true
        }
        setNavigationOnClickListener {
            listerner?.navClicked()
        }
    }

    lateinit var listerner: JSBarClickListerner


    interface JSBarClickListerner {
        fun menuClicked(menuId: Int)
        fun navClicked()
    }
}