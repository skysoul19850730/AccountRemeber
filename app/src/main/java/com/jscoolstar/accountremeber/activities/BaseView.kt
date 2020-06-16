package com.jscoolstar.accountremeber.activities

interface BaseView<T> {
    var presenter: T
    fun showToast(text:String)
    fun showToast(textId:Int)
}