package com.jscoolstar.accountremeber.activities.edit.presenters

import com.jscoolstar.accountremeber.activities.BasePresenter
import com.jscoolstar.accountremeber.dataprovider.dataentity.Account
import com.jscoolstar.accountremeber.dataprovider.dataentity.Cate
import com.jscoolstar.accountremeber.dataprovider.dataentity.User

interface IEditPresenter:BasePresenter {
    fun getUser():User?
    fun getDefaultCate():Cate
    fun addCateWithName(name:String)
    fun saveAccount(account: Account)
}