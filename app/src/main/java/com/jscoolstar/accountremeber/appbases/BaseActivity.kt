package com.jscoolstar.accountremeber.appbases

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 *author:skysoul
 *create time:2020/4/6 4:45 PM
 *description:
 */
abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
//        setSupportActionBar(mToolbar)
        initData()
    }

    abstract fun getLayoutResId(): Int
    abstract fun initView()
    abstract fun initData()

}