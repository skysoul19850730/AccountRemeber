package com.jscoolstar.accountremeber.activities

import com.jscoolstar.accountremeber.apps.MApplication

interface BasePresenter {
    fun start()
    fun getString(id:Int):String{
        return MApplication.getInstance().getString(id)
    }
    fun destory()
}