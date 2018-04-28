package com.jscoolstar.accountremeber.activities

import android.content.Context
import android.os.Build
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.view.MenuItem
import com.jscoolstar.accountremeber.R

/**
 * Created by Administrator on 2018/4/11.
 */
class HomeToolbar4Edit @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0 ) : Toolbar(context, attrs, defStyleAttr){


    lateinit var menu_delete: MenuItem

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
        inflateMenu(R.menu.menu_home4edit)
        menu_delete = menu.findItem(R.id.menu_delete)

        setOnMenuItemClickListener {
            when (it.itemId) {

                R.id.menu_delete -> listerner?.delete()

            }
            return@setOnMenuItemClickListener true
        }
        setNavigationOnClickListener{
            listerner?.cancle()
        }
    }

    lateinit var listerner: HomeBar4EditClickListerner


    interface HomeBar4EditClickListerner {
        fun cancle()
        fun delete()
    }
}